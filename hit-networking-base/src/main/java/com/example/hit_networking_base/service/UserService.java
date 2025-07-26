package com.example.hit_networking_base.service;
import com.example.hit_networking_base.domain.dto.request.*;
import com.example.hit_networking_base.domain.dto.response.*;
import com.example.hit_networking_base.domain.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


public interface UserService {
    User findUserByUsername (String username);
    User findUserById(long id);
    ChangePasswordResponseDTO changePassword(ChangePasswordRequest changePasswordRequest);
    UserInfoResponseDTO getUserInfo();
    UserInfoResponseDTO updateUser(UpdateUserRequest updateUserRequest);
    UserResponseDTO updateUser(Long id, RequestUpdateUserDTO request);
    UserResponseDTO createUser(RequestCreateUserDTO request);
    boolean addAdmin(String adminNane, String password);
    Map<String, Object> getAllUser(int page, int size);
    List<UserExportDTO> getAllUserToSendEmail();
    boolean resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO);
    UserDetailResponseDTO deleteUser(String username);
    UserDetailResponseDTO getUserDetailByAdmin(Long userId);
    UserResponseDTO restoreUser(RestoreUserRequestDTO restoreUserRequestDTO);
    void setActivated(User user);
}
