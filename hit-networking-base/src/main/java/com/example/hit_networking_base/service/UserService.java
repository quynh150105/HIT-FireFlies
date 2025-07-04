package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.ChangePasswordRequest;
import com.example.hit_networking_base.domain.dto.request.UpdateUserRequest;
import com.example.hit_networking_base.domain.dto.response.ChangePasswordResponseDTO;
import com.example.hit_networking_base.domain.dto.response.UserInfoResponseDTO;
import com.example.hit_networking_base.domain.entity.User;

public interface UserService {
    User findUserByUsername (String username);
    ChangePasswordResponseDTO changePassword(ChangePasswordRequest changePasswordRequest);
    UserInfoResponseDTO getUserInfo();
    UserInfoResponseDTO updateUser(UpdateUserRequest updateUserRequest);

}
