package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.SuccessMessage;
import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.request.ResetPasswordRequest;
import com.example.hit_networking_base.domain.dto.response.AuthResponseDTO;
import com.example.hit_networking_base.domain.dto.response.ResetPasswordResponseDTO;
import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.service.AuthService;
import com.example.hit_networking_base.service.SendEmailService;
import com.example.hit_networking_base.service.TokenService;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final SendEmailService sendEmailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponseDTO login(AuthRequest authRequest) {
        User user = userService.findUserByUsername(authRequest.getUsername());
        if(!user.isActivate())
            throw new BadRequestException(ErrorMessage.Auth.ERR_NOT_ACTIVATE);
        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash()))
            throw new BadRequestException(ErrorMessage.User.ERR_INVALID_PASSWORD);
        String token = tokenService.generateToken(userMapper.toUserExportDTO(user));
        return new AuthResponseDTO(
                user.getUsername(),
                user.getRole().name(),
                user.getAvatarUrl(),
                token
        );
    }

    @Override
    public ResetPasswordResponseDTO resetPassword(ResetPasswordRequest request) {
        UserExportDTO user =userMapper
                .toUserExportDTO(userService.findUserByUsername(request.getUsername()));
        if (!user.getEmail().equals(request.getEmail())) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_EMAIL, new long[]{});
        }
        sendEmailService.seddEmailToUserResetPassword(user);
        return new ResetPasswordResponseDTO(request.getUsername(), SuccessMessage.Auth.SEND_EMAIL_SUCCESS);
    }
}
