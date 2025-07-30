package com.example.hit_networking_base.util;

import com.example.hit_networking_base.constant.ErrorMessage;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EmailTemplateLoader {

    private static String loadTemplate(String path, Map<String, String> placeholders, String errorMessage) {
        try {
            byte[] bytes = new ClassPathResource(path).getInputStream().readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                content = content.replace(entry.getKey(), entry.getValue());
            }
            return content;
        } catch (IOException e) {
            throw new RuntimeException(errorMessage, e);
        }
    }

    public static String loadSendResetPassTemplate(String fullName, String url) {
        return loadTemplate("templates/sendResetPassword.html",
                Map.of("{{fullName}}", fullName, "{{link-set-new-pass}}", url),
                ErrorMessage.Email.ERR_SEND_RESET_PASS);
    }

    public static String loadSendCreateUserTemplate(String fullName, String username, String url) {
        return loadTemplate("templates/sendNotificationCreateUser.html",
                Map.of("{{fullname}}", fullName, "{{username}}", username, "{{link-set-new-pass}}", url),
                ErrorMessage.Email.ERR_SEND_CREATE_USER);
    }

    public static String loadSendCreatePostTemplate(String fullName) {
        return loadTemplate("templates/sendNotification.html",
                Map.of("{{fullName}}", fullName, "{{link-home}}", "https://hitnetwork.onrender.com/home"),
                ErrorMessage.Email.ERR_SEND_CREATE_POST);
    }
}
