package com.example.hit_networking_base.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIALS = "@$!%*?&";

    private static final String ALL = LOWER + UPPER + DIGITS + SPECIALS;
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(){
        // default 8 characters
        int length = 8;

        List<Character> password = new ArrayList<>();
        password.add(LOWER.charAt(random.nextInt(LOWER.length())));
        password.add(UPPER.charAt(random.nextInt(UPPER.length())));
        password.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.add(SPECIALS.charAt(random.nextInt(SPECIALS.length())));

        for(int i = 4; i< length; i++){
            password.add(ALL.charAt(random.nextInt(ALL.length())));
        }

        StringBuilder result = new StringBuilder();
        for(char c: password){
            result.append(c);
        }
        return result.toString();
    }
    public static void main(String[] args) {
        System.out.println(generatePassword());
    }


}