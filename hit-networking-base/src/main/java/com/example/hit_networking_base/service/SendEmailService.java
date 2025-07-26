package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.response.UserExportDTO;

import java.util.List;

public interface SendEmailService {
    void sendEmailToCreateUser(List<UserExportDTO> users);
    void seddEmailToUserResetPassword(UserExportDTO user);
    void sendEmailWhenCreatePost(List<UserExportDTO> users);
}
