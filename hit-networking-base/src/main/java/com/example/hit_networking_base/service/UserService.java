package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.RequestCUDUserDTO;
import com.example.hit_networking_base.domain.dto.request.RequestSearchUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponseDTO createUser(RequestCUDUserDTO request);

    UserResponseDTO searchUser(RequestSearchUserDTO request);

    UserResponseDTO updateUser(RequestSearchUserDTO request);

    Page<UserResponseDTO> getAll(int page, int size);
}
