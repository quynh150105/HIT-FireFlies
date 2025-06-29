package com.example.hit_networking_base.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmailTemplateLoader {
    public static String loadTemplate(String username, String newPassword){
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/sendEmail.html");
            byte[] bytes = classPathResource.getInputStream().readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            return content
                    .replace("{{username}}", username)
                    .replace("{{password}}", newPassword);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read sendEmail file",e);
        }
    }
}
