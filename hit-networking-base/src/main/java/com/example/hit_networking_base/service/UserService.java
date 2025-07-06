package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.RequestCreateUserDTO;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface UserService {
    UserResponseDTO updateUser(RequestUpdateUserDTO request);

    UserResponseDTO createUser(RequestUpdateUserDTO request);

}
