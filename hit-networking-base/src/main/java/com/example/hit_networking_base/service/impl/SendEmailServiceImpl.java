package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendEmailServiceImpl implements SendEmailService {
    private final JavaMailSender mailSender;


    @Override
    public void sendEmailToUser(List<UserExportDTO> users) {
            for(UserExportDTO user : users){
                sendMail(user);
            }
    }

    public void sendMail(UserExportDTO user){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Information Your Account");
        message.setText("Chào bạn " + ",\n\n" +
                "Tài khoản của bạn đã được tạo thành công.\n" +
                "Tài khoản: " + user.getUsername() + "\n" +
                "Mật khẩu: " + user.getPassword()+ "\n\n" +
                "Vui lòng đổi mật khẩu sau khi đăng nhập.");
        mailSender.send(message);
    }

}
