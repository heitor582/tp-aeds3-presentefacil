package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.management.RuntimeErrorException;

import model.User;
import repository.ExtensibleHash;
import repository.GlobalMemory;
import repository.UserRepository;
import repository.idEmailIndexPair;

public class UserController {
    public final static UserController INSTANCE = new UserController();
    private UserRepository repository;
    private ExtensibleHash<idEmailIndexPair> hash;

    public UserController() {
        try {
            this.repository = new UserRepository();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findUserById(int id) {
        try {
            return repository.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean login(String email,String password){
        try{        
            MessageDigest.getInstance("MD5");
            // User user = repository.read(hash.read(email.hashCode()));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }
    // public boolean logout(){}

    public int create(User user){
        int id = -1;
        try{
            user.setHashPassword(toMd5(user.getHashPassword()));
           id =  repository.create(user);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return id;
    }

    // public int update(){}

    public boolean delete(){
        try {
            if(!repository.delete(GlobalMemory.getUserId())){// tem que ver qq vai fzr se vai deixar deletar por causa das listas ou n ou, se sim deletar as listas tbm
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        GlobalMemory.logout();
        return true;
    }

    public String toMd5 (String password){
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
