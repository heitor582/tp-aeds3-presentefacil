package controller;

import java.util.List;

import model.User;
import repository.GlobalMemory;
import repository.user.UserRepository;
import shared.Cryptography;
import shared.NonBlank;

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
        } catch (final Exception e) {
            return null;
        }
    }

    public boolean login(final String email, final String password){
        try{        
            User user = this.repository.findByEmail(email);
            if(user == null) return false;
            if(!user.getHashPassword().equals(Cryptography.toMd5(password))) return false;
            GlobalMemory.setUserId(user.getId());
            return user.isActive();
        }catch(final Exception e){
            return false;
        }
    }

    public List<String> getUserQuestion(final String email, final String password){
        try{        
            User user = this.repository.findByEmail(email);
            if(user == null) return null;
            if(!user.getHashPassword().equals(Cryptography.toMd5(password))) return null;
            GlobalMemory.setUserId(user.getId());
            return List.of(user.getSecretQuestion(), user.getSecretAnswer());
        }catch(final Exception e){
            List.of();
        }
        return List.of();
    }

    public boolean logout(){
        GlobalMemory.logout();
        return GlobalMemory.isLogout();
    }

    public int create(
        final String name,
        final String email,
        final String password,
        final String secretQuestion,
        final String secretAnswer
    ){
        int id = -1;
        try{
            User user = User.create(name, email, password, secretQuestion, secretAnswer);
            id = this.repository.create(user);
        }catch(final Exception e){
            return -1;
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

                boolean isNewPassword = NonBlank.isValid(password) && !password.equals(oldUser.getHashPassword());
               
                User newUser = User.from(
                    oldUser.getId(), 
                    name, 
                    email, 
                    isNewPassword ? Cryptography.toMd5(password.trim()) : oldUser.getHashPassword(), 
                    secretQuestion, 
                    secretAnswer, 
                    true
                );

                this.repository.update(newUser);
                repository.updateIndirectIndex(newUser, oldEmail);
            } else {
                System.out.println("Usuário com ID " + id + " não encontrado.");
            }
        } catch (final Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public boolean delete(){
        try {
            if(!this.repository.delete(GlobalMemory.getUserId())){//TODO: tem que ver qq vai fzr se vai deixar deletar por causa das listas ou n ou, se sim deletar as listas tbm
                return false;
            }
        } catch (final Exception e) {
            return false;
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
        } catch (final Exception e) {
            return false;
        }
    }    
}
