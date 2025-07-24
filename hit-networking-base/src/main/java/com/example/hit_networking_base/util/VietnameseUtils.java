package com.example.hit_networking_base.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class VietnameseUtils {
    public static String removeAccents(String input){
        if(input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Loại bỏ các dấu (combining diacritical marks)
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("")
                .replace("đ", "d")
                .replace("Đ", "D");

    }
}
