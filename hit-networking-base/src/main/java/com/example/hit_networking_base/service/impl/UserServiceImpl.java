package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.config.PasswordConfig;
import com.example.hit_networking_base.domain.dto.request.RequestCreateUserDTO;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final UserRepository repository;
    private final UserMapper mapper;

    private final PasswordConfig passwordEncoder;

    @Override
    public UserResponseDTO updateUser(RequestUpdateUserDTO request) {

        // 1. Tìm user theo userName
        User user = repository.findByUserName(request.getUserName());
        if (user == null) {
            throw new UserException("Không tìm thấy người dùng.");
        }

        // 2. Cập nhật thông tin (ngoại trừ userName)
        user.setFullName(request.getFullName());
        user.setDob(request.getDob());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());

        // 3. Mã hóa password trước khi lưu
        String hashedPassword = passwordEncoder.passwordEncoder().encode(request.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        // 4. Lưu lại vào database
        repository.save(user);

        // 5. Trả về dữ liệu sau khi cập nhật
        return mapper.toUserResponseDTO(user);

    }

    @Override
    public UserResponseDTO createUser(RequestUpdateUserDTO request) {
        if(repository.existsByUserName(request.getUserName())){
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



