package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.config.JwtProperties;
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
import com.example.hit_networking_base.exception.ForbiddenException;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.service.AuthService;
import com.example.hit_networking_base.service.SendEmailService;
import com.example.hit_networking_base.service.TokenService;
import com.example.hit_networking_base.service.UserService;
import com.example.hit_networking_base.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final JwtProperties jwtProperties;


    @Override
    public AuthResponseDTO login(AuthRequest authRequest, HttpServletResponse response) {
        User user = userService.findUserByUsername(authRequest.getUsername());
        if (!user.isActivate())
            throw new BadRequestException(ErrorMessage.Auth.ERR_NOT_ACTIVATE);
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash()))
            throw new BadRequestException(ErrorMessage.User.ERR_INVALID_PASSWORD);

        UserExportDTO userDTO = userMapper.toUserExportDTO(user);
        String refreshToken = tokenService.generateRefreshToken(userDTO);
        String accessToken = tokenService
                .generateToken(userDTO, JwtUtils.getTokenPass(refreshToken, jwtProperties.getSecret()));

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // để true nếu bạn dùng HTTPS
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwtProperties.getRefreshExpirationTime()));
        response.addCookie(cookie);


        return new AuthResponseDTO(
                user.getUsername(),
                user.getRole().name(),
                user.getAvatarUrl(),
                accessToken
        );
    }


    @Override
    public ResetPasswordResponseDTO resetPassword(ResetPasswordRequest request) {
        User user = userService.findUserByUsername(request.getUsername());
        UserExportDTO userExportDTO =userMapper
                .toUserExportDTO(user);
        if (!user.getEmail().equals(request.getEmail())) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_EMAIL);
        }
        userService.setActivated(user);
        sendEmailService.seddEmailToUserResetPassword(userExportDTO);
        return new ResetPasswordResponseDTO(request.getUsername(), SuccessMessage.Auth.SEND_EMAIL_SUCCESS);
    }

    @Override
    public String refreshToken(HttpServletRequest request) {
        String refreshToken = null;
        if(request.getCookies() != null){
            for(Cookie cookie:request.getCookies()){
                if(cookie.getName().equals("refreshToken")){
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        System.out.println("REFRESH TOKEN: " + refreshToken);
        if(refreshToken == null) {
            System.out.println("Không có token");
            throw new ForbiddenException(ErrorMessage.Auth.ERR_INVALID_TOKEN_REFRESH);
        }
        if(!tokenService.verifyTokenRefresh(refreshToken)){
            System.out.println("Token hêt hạn hoặc sai");
            throw new ForbiddenException(ErrorMessage.Auth.ERR_INVALID_TOKEN_REFRESH);
        } else {
            User user = userService.findUserByUsername(JwtUtils.extractUsername(refreshToken, jwtProperties.getSecret()));
            String tokenPass = JwtUtils.getTokenPass(refreshToken, jwtProperties.getSecret());
            if(!passwordEncoder.matches(tokenPass, user.getCheckToken())){
                System.out.println("Mật khẩu token không đúng");
                System.out.println("Mât khẩu trong token: " + tokenPass);
                System.out.println("Người đăng nhập: " + user.getUsername() + "----  Mât khẩu trong user: " + user.getCheckToken());
                throw new ForbiddenException(ErrorMessage.Auth.ERR_INVALID_TOKEN_REFRESH);
            }
            return tokenService.generateToken(userMapper.toUserExportDTO(user), tokenPass);
        }
    }
}
