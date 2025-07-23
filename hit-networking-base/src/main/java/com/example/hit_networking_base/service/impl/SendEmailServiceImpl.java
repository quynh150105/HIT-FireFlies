package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.service.SendEmailService;
import com.example.hit_networking_base.service.TokenService;
import com.example.hit_networking_base.util.EmailTemplateLoader;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SendEmailServiceImpl implements SendEmailService {
    private final JavaMailSender mailSender;
    private final TokenService tokenService;

    @Value("${reset.password.url}")
    private String linkResetPassword;

    @Override
    public void sendEmailToCreateUser(List<UserExportDTO> users) {
        for(UserExportDTO user : users){
            String url = linkResetPassword + tokenService.generateToken(user);
            String html = EmailTemplateLoader
                    .loadSendCreateUserTemplate(user.getFullName(),user.getUsername(), url);
            sendMail(user, html, "Account creation notice");
        }
    }

    @Override
    public void seddEmailToUserResetPassword(UserExportDTO user) {
        String url = linkResetPassword + tokenService.generateToken(user);
        String html = EmailTemplateLoader
                .loadSendResetPassTemplate(user.getFullName(), url);
        sendMail(user, html, "Reset new password");
    }

    @Override
    public void sendEmailWhenCreatePost(List<UserExportDTO> users) {
        String linkHome = "https://hitnetwork.onrender.com/home";
        for(UserExportDTO user: users) {
            String html = EmailTemplateLoader
                    .loadSendCreatePostTemplate(user.getFullName());
            sendMail(user, html, "Have new post");
        }
    }

    private void sendMail(UserExportDTO user, String html, String subject){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(ErrorMessage.Auth.ERR_SEND_EMAIL, e);
        }
    }
}
