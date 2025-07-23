package com.example.hit_networking_base.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenPassword {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBER = "0123456789";
    private static final int PASSWORD_LENGTH = 10;

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();

        passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordChars.add(NUMBER.charAt(random.nextInt(NUMBER.length())));

        String allChars = UPPERCASE + LOWERCASE + NUMBER;
        for (int i = 3; i < PASSWORD_LENGTH; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        Collections.shuffle(passwordChars, random);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
