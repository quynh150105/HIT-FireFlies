package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.SuccessMessage;
import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.request.ResetPasswordRequest;
import com.example.hit_networking_base.domain.dto.response.AuthResponseDTO;
import com.example.hit_networking_base.domain.dto.response.ResetPasswordResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.AuthService;
import com.example.hit_networking_base.service.TokenService;
import com.example.hit_networking_base.service.UserService;
import com.example.hit_networking_base.util.EmailTemplateLoader;
import com.example.hit_networking_base.util.GenPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponseDTO login(AuthRequest authRequest) {
        User user = userService.findUserByUsername(authRequest.getUsername());

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash()))
            throw new BadRequestException(ErrorMessage.User.ERR_INVALID_PASSWORD);

        String token = tokenService.generateToken(user);

        return new AuthResponseDTO(
                user.getUsername(),
                user.getRole().name(),
                user.getAvatarUrl(),
                token
        );
    }

    @Override
    public ResetPasswordResponseDTO resetPassword(ResetPasswordRequest request) {
        User user = userService.findUserByUsername(request.getUsername());

        if (!user.getEmail().equals(request.getEmail())) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_EMAIL, new long[]{});
        }

        String newPassword = GenPassword.generatePassword();
        String html = EmailTemplateLoader.loadTemplate(user.getUsername(), newPassword);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Rest password");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(ErrorMessage.Auth.ERR_SEND_EMAIL,e);
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new ResetPasswordResponseDTO(request.getUsername(), SuccessMessage.Auth.SEND_EMAIL_SUCCESS);
    }
}
