package com.example.hit_networking_base.domain.mapstruct;


import com.example.hit_networking_base.domain.dto.request.RequestCreateUserDTO;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.domain.dto.response.UserInfoResponseDTO;
import com.example.hit_networking_base.domain.dto.response.UserPostResponseDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import org.mapstruct.Mapper;
import java.util.List;

import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")

public interface UserMapper {
    User toUser(RequestCreateUserDTO request);
   @Mapping(source = "username", target = "username")
    UserResponseDTO toUserResponseDTO(User user);

    List<UserResponseDTO> TolistUserResponseDTO(List<User> list);

    UserInfoResponseDTO toUserInforResponseDTO(User user);

    UserPostResponseDTO toUserPostResponseDTO(User user);

    List<UserExportDTO> toUserExportDTO(List<User> list);

}
