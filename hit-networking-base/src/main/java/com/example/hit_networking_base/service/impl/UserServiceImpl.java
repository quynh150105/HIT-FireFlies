package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.*;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
//import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.hit_networking_base.domain.dto.request.ChangePasswordRequest;
import com.example.hit_networking_base.domain.dto.request.UpdateUserRequest;
import com.example.hit_networking_base.domain.dto.response.ChangePasswordResponseDTO;
import com.example.hit_networking_base.domain.dto.response.UserInfoResponseDTO;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.service.ImageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final UserRepository repository;
    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Override
    public UserResponseDTO updateUser(RequestUpdateUserDTO request) {

        // 1. Tìm user theo userName
        User user = findUserByUsername(request.getUsername());
        if (user == null) {
            throw new UserException("Không tìm thấy người dùng.");
        }

        // 2. Cập nhật thông tin (ngoại trừ userName)
        user.setFullName(request.getFullName());
        user.setDob(request.getDob());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());

        // 3. Mã hóa password trước khi lưu
        String hashedPassword = passwordEncoder.encode(request.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        // 4. Lưu lại vào database
        repository.save(user);

        // 5. Trả về dữ liệu sau khi cập nhật
        return mapper.toUserResponseDTO(user);

    }

    @Override
    public UserResponseDTO createUser(RequestUpdateUserDTO request) {
        if(repository.existsByUsername(request.getUsername())){
            throw new UserException("User da ton tai");
        }
        if(repository.existsByEmail(request.getEmail())){
            throw new UserException("email da ton tai");
        }
        User user = mapper.toUser(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        User saveUser = repository.save(user);
        return mapper.toUserResponseDTO(saveUser);
    }


    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USER_NAME, new long[]{}));
    }

    @Override
    public ChangePasswordResponseDTO changePassword(ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException(ErrorMessage.User.ERR_NOT_AUTHENTICATED);
        }
        String username = authentication.getName();
        User user = findUserByUsername(username);

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPasswordHash())){
            throw new BadRequestException(ErrorMessage.User.ERR_INVALID_PASSWORD);
        }

        if(passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPasswordHash())){
            throw new BadRequestException(ErrorMessage.User.ERR_SAME_PASSWORD);
        }

        user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return new ChangePasswordResponseDTO(SuccessMessage.User.PASSWORD_CHANGE_SUCCESS);
    }

    @Override
    public UserInfoResponseDTO getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new RuntimeException(ErrorMessage.User.ERR_NOT_AUTHENTICATED);
        }

        User user = findUserByUsername(authentication.getName());
        return userMapper.toUserInforResponseDTO(user);
    }

    @Override
    public UserInfoResponseDTO updateUser(UpdateUserRequest updateUserRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = findUserByUsername(authentication.getName());

        MultipartFile file = updateUserRequest.getAvatar();
        List<String> avatarUrls = new ArrayList<>();

        if (file != null && !file.isEmpty()) {
            try {
                avatarUrls = imageService.uploadImage(new MultipartFile[]{file}, TargetType.USER, user.getUserId());
                System.out.println("======> Nhận được file: " + file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(ErrorMessage.Image.ERR_UPLOAD, e);
            }
        }

        user.setFullName(updateUserRequest.getFullName());
        user.setEmail(updateUserRequest.getEmail());
        user.setPhone(updateUserRequest.getPhone());
        user.setGender(updateUserRequest.getGender());
        user.setDob(updateUserRequest.getDob());

        if (!avatarUrls.isEmpty()) {
            user.setAvatarUrl(avatarUrls.get(0));
        }

        userRepository.save(user);
        return userMapper.toUserInforResponseDTO(user);
    }


    @Override
    public boolean addAdmin(String adminName, String adminPassword) {
        if(userRepository.existsByRole(Role.BQT))
            return false;

        User admin = new User();
        admin.setUsername(adminName);
        admin.setPasswordHash(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.BQT);
        admin.setGender(Gender.OTHER);
        admin.setDob(LocalDate.of(2000, 1, 1));
        admin.setFullName("Administrator");
        admin.setEmail("admin@example.com");
        admin.setPhone("0123456789");
        admin.setCreatedAt(LocalDate.now());
        userRepository.save(admin);

        return true;
    }

    @Override
    public Map<String, Object> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> pageResult = userRepository.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("items", pageResult.getContent());
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());
        response.put("currentPage", pageResult.getNumber());
        response.put("pageSize", pageResult.getSize());

        return response;

    }

}

