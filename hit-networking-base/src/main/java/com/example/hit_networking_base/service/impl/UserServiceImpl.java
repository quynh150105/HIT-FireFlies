package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.SuccessMessage;
import com.example.hit_networking_base.domain.dto.request.ChangePasswordRequest;
import com.example.hit_networking_base.domain.dto.response.ChangePasswordResponseDTO;
import com.example.hit_networking_base.domain.dto.response.UserInfoResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USER_NAME, new long[]{}));
    }

    @Override
    public ChangePasswordResponseDTO changePassword(ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException(ErrorMessage.User.ERR_NOT_AUTHENTICATED);
        }
        String username = authentication.getName();
        User user = findUserByUsername(username);

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPasswordHash())){
            throw new BadRequestException(ErrorMessage.User.ERR_INVALID_PASSWORD);
        }

        if(passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPasswordHash())){
            throw new BadRequestException(ErrorMessage.User.ERR_SAME_PASSWORD);
        }

        user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return new ChangePasswordResponseDTO(SuccessMessage.User.PASSWORD_CHANGE_SUCCESS);
    }

    @Override
    public UserInfoResponseDTO getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new RuntimeException(ErrorMessage.User.ERR_NOT_AUTHENTICATED);
        }

        User user = findUserByUsername(authentication.getName());
        return userMapper.toUserInforResponseDTO(user);
    }
}
