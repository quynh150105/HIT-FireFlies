package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.SetCheckTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SetCheckTokenServiceImpl implements SetCheckTokenService {

    private final UserRepository userRepository;

    @Override
    public void setCheckToken(UserExportDTO userExportDTO, String passwordToken) {
        User user = userRepository.findByUsername(userExportDTO.getUsername()).orElseThrow(()
                -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USER_NAME));
        user.setCheckToken(passwordToken);
        userRepository.save(user);
    }

    @Override
    public String getTokenPass(String username) {
        return userRepository.findCheckTokenByUsername(username);
    }

}
