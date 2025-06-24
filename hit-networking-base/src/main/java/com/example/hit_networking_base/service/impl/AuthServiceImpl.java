package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.response.AuthResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.AuthService;
import com.example.hit_networking_base.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public AuthResponseDTO login(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(()
                -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USER_NAME, new long[]{}));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash()))
            throw new BadRequestException(ErrorMessage.User.ERR_INVALID_PASSWORD);

        String token = tokenService.generateToken(user);

        return new AuthResponseDTO(
                user.getUsername(),
                user.getRole().name(),
                token
        );
    }
}
