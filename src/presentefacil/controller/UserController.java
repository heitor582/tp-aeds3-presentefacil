package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import model.User;
import repository.GlobalMemory;
import repository.user.UserRepository;

public class UserController {
    public final static UserController INSTANCE = new UserController();
    private UserRepository repository;

    private UserController() {
        try {
            this.repository = new UserRepository();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public User findUserById(final int id) {
        try {
            return this.repository.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean login(final String email, final String password){
        try{        
            User user = this.repository.findByEmail(email);
            if(user == null) return false;
            if(!user.getHashPassword().equals(toMd5(password))) return false;
            GlobalMemory.setUserId(user.getId());
            return user.isActive();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getUserQuestion(final String email, final String password){
        try{        
            User user = this.repository.findByEmail(email);
            if(user == null) return null;
            if(!user.getHashPassword().equals(toMd5(password))) return null;
            GlobalMemory.setUserId(user.getId());
            return List.of(user.getSecretQuestion(), user.getSecretAnswer());
        }catch(Exception e){
            e.printStackTrace();
        }
        return List.of();
    }

    public boolean logout(){
        GlobalMemory.logout();
        return true;
    }

    public int create(final User user){
        int id = -1;
        try{
            user.setHashPassword(toMd5(user.getHashPassword()));
            id = this.repository.create(user);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return id;
    }

    public void updateUser(
        final int id, 
        final String name,
        final String email, 
        final String password, 
        final String secretQuestion,
        final String secretAnswer
        ) {
        try {
            final User oldUser = findUserById(id);
            if (oldUser != null) {
                String oldEmail = oldUser.getEmail();

                boolean isNewPassword = password != null && !password.isBlank() && !password.equals(oldUser.getHashPassword());
               
                User newUser = User.from(
                    oldUser.getId(), 
                    name, 
                    email, 
                    isNewPassword ? toMd5(password.trim()) : oldUser.getHashPassword(), 
                    secretQuestion, 
                    secretAnswer, 
                    true
                );

                this.repository.update(newUser);
                repository.updateIndirectIndex(newUser, oldEmail);
            } else {
                System.out.println("Usuário com ID " + id + " não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public boolean delete(){
        try {
            if(!this.repository.delete(GlobalMemory.getUserId())){//TODO: tem que ver qq vai fzr se vai deixar deletar por causa das listas ou n ou, se sim deletar as listas tbm
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.logout();
        return true;
    }

    public boolean changeStatus(final boolean active) {
        int id = GlobalMemory.getUserId();
        try {
            User user = this.findUserById(id);
            user.changeStatus(active);

            GiftListController.INSTANCE.changeStatusByUserId(false);
            
            return repository.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String toMd5(final String password){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hashBytes){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Erro ao gerar hash MD5",e);
        }
    }
}
