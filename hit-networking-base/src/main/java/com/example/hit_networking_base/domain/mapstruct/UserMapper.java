package com.example.hit_networking_base.domain.mapstruct;


import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface UserMapper {
   UserResponseDTO toUserResponseDTO(User user);
}
