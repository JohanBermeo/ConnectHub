package auth;

import java.util.Date;

import model.DataManager;
import model.FileHandler;
import model.User;

/**
 * Separación de responsabilidades - Autenticación
 */
public class AuthenticationService {
    private DataManager<User> userController;
    private FileHandler<User> userDataHandler;
    
    public AuthenticationService() {
        this.userController = new DataManager<>();
        this.userDataHandler = new FileHandler<>("users.dat");
    }
    
    public boolean login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null || !user.validatePassword(password)) {
            return false;
        }
        return true;
    }
    
    public User createAccount(String username, String password, Date birthday) throws Exception {
        validateUserData(username, password, birthday);
        
        if (getUserByUsername(username) != null) {
            throw new Exception("El usuario ya existe");
        }
        
        User newUser = new User(username, password, birthday);
        userController.addData(newUser);
        return newUser;
    }
    
    private User getUserByUsername(String username) {
        for (User user : userController.getData()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    private void validateUserData(String username, String password, Date birthday) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new Exception("Username no puede estar vacío");
        }
        if (password == null || password.length() < 8) {
            throw new Exception("Password debe tener al menos 8 caracteres");
        }
        if (birthday == null) {
            throw new Exception("Fecha de nacimiento es requerida");
        }
        // Más validaciones...
    }
}