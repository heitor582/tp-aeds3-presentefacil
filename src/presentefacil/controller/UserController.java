package controller;

import model.User;
import repository.GlobalMemory;
import repository.UserRepository;

public class UserController {
    public final static UserController INSTANCE = new UserController();
    private UserRepository repository;

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

    // public boolean login(String email){}
    // public boolean logout(){}
    // public int create(){}
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
}
