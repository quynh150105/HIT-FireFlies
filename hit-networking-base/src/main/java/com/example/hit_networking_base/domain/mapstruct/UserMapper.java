package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.request.RequestCUDUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(target = "gender", expression = "java(dto.getGender() != null ? dto.getGender() : com.example.hit_networking_base.domain.entity.User.Gender.NAM)")
   User toUser(RequestCUDUserDTO request);

    UserResponseDTO toUserResponseDTO(User user);
}
