package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    // Gera o hash da senha usando BCrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    // Verifica se a senha está correta
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
} 