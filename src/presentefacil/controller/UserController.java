package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import model.User;
import repository.GlobalMemory;
import repository.UserRepository;

public class UserController {
    public final static UserController INSTANCE = new UserController();
    private UserRepository repository;

    private UserController() {
        try {
            this.repository = new UserRepository();
        } catch (Exception e) {
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
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return true;
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
            User user = findUserById(id);
            if (user != null) {
                String oldEmail = user.getEmail();

                user.setName(name);
                user.setEmail(email);

                if (password != null && !password.isBlank() && !password.equals(user.getHashPassword()))
                    user.setHashPassword(toMd5(password.trim()));

                user.setSecretQuestion(secretQuestion);
                user.setSecretAnswer(secretAnswer);

                this.repository.update(user);
                repository.updateIndirectIndex(user, oldEmail);
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
