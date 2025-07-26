package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.response.UserExportDTO;

public interface SetCheckTokenService {
    void setCheckToken(UserExportDTO userExportDTO, String passToken);
    String getTokenPass(String username);
}
