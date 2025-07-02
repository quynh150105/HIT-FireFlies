package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface UserService {
    UserResponseDTO updateUser(RequestUpdateUserDTO request);

//    UserResponseDTO searchUser(RequestSearchUserDTO request);
//
//    UserResponseDTO updateUser(RequestSearchUserDTO request);

    Page<UserResponseDTO> getAll(int page, int size);

//    List<UserResponseDTO> getAll();
}
