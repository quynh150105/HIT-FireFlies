package com.example.hit_networking_base.util;

import com.example.hit_networking_base.constant.ErrorMessage;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmailTemplateLoader {
    public static String loadSendResetPassTemplate(String fullName, String url){
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/sendResetPassword.html");
            byte[] bytes = classPathResource.getInputStream().readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            return content
                    .replace("{{fullName}}", fullName)
                    .replace("{{link-set-new-pass}}", url);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.Email.ERR_SEND_RESET_PASS,e);
        }
    }

    public static String loadSendCreateUserTemplate(String fullName, String username, String url){
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/sendNotificationCreateUser.html");
            byte[] bytes = classPathResource.getInputStream().readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            return content
                    .replace("{{fullname}}", fullName)
                    .replace("{{username}}", username)
                    .replace("{{link-set-new-pass}}", url);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.Email.ERR_SEND_CREATE_USER,e);
        }
    }

    public static String loadSendCreatePostTemplate(String fullName){
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/sendNotification.html");
            byte[] bytes = classPathResource.getInputStream().readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            return content
                    .replace("{{fullName}}", fullName)
                    .replace("{{link-home}}", "https://hitnetwork.onrender.com/home");
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.Email.ERR_SEND_CREATE_POST,e);
        }
    }
}
