package dao;

import model.User;

public class UserDAO {
    public static void save(User user) {
        System.out.println(">> [Save user - " + user + " - not implemented yet]");
    }

    public static User findByEmail(String email) {
        System.out.println(">> [Find user by email - " + email + " - not implemented yet]");
        return null;
    }

    public static String hashPassword(String password) {
        System.out.println(">> [Hash password - " + password + " - not implemented yet]");
        return "";
    }
}
