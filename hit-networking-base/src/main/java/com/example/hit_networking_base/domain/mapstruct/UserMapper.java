package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import org.mapstruct.Mapper;
import java.util.List;

import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")

public interface UserMapper {
  //  @Mapping(target = "gender", expression = "java(dto.getGender() != null ? dto.getGender() : com.example.hit_networking_base.domain.entity.User.Gender.NAM)")
    User toUser(RequestUpdateUserDTO request);

  @Mapping(source = "userName", target = "userName")
  UserResponseDTO toUserResponseDTO(User user);

    List<UserResponseDTO> TolistUserResonseDTO(List<User> list);



}
