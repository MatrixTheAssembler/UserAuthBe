package de.frauas.userauth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SecurityUtil {

    @Value("${security.pepper}")
    private String pepper;

    public String generateSalt() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int index = secureRandom.nextInt(characters.length());
            salt.append(characters.charAt(index));
        }
        return salt.toString();
    }

    public String getPepper() {
        return pepper;
    }
}
