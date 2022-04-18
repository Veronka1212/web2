package util;

import java.util.Base64;

public class PasswordEncryptor {
    public static String encryption(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
