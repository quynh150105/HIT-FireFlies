package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.config.PasswordConfig;
import com.example.hit_networking_base.domain.dto.request.RequestCUDUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final UserRepository repository;
    private final UserMapper mapper;

    private final PasswordConfig passwordEncoder;

    @Override
    public UserResponseDTO createUser(RequestCUDUserDTO request) {
        if(repository.existsByUsername(request.getUsername())){
            throw new UserException("User da ton tai");
        }
        if(repository.existsByEmail(request.getEmail())){
            throw new UserException("email da ton tai");
        }
        User user = mapper.toUser(request);
        user.setPasswordHash(passwordEncoder.passwordEncoder().encode(request.getPasswordHash()));
        User saveUser = repository.save(user);
        return mapper.toUserResponseDTO(saveUser);
    }


}
