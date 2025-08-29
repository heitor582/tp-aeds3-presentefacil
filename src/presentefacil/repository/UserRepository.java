package repository;

import model.User;

public class UserRepository extends DBFile<User> {
    public UserRepository() throws Exception {
        super(User.class);
    }
}
