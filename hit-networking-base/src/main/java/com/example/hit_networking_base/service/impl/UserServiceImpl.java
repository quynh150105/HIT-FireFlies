package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.config.JwtProperties;
import com.example.hit_networking_base.constant.*;
import com.example.hit_networking_base.domain.dto.request.*;
import com.example.hit_networking_base.domain.dto.response.*;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.*;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.SendEmailService;
import com.example.hit_networking_base.service.UserService;
import com.example.hit_networking_base.util.GenPassword;
import com.example.hit_networking_base.util.JwtUtils;
import com.example.hit_networking_base.util.VietnameseUtils;
import lombok.RequiredArgsConstructor;
//import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final SendEmailService sendEmailService;
    private final JwtProperties jwtProperties;

    @Override
    public UserResponseDTO updateUser(Long id, RequestUpdateUserDTO request) {
        User user = findUserById(id);
        if(!user.getRole().equals(Role.BQT) &&
                !user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new BadRequestException(ErrorMessage.User.ERR_NOT_ENOUGH_RIGHTS);
        }
        user.setFullName(request.getFullName());
        user.setDob(request.getDob());
        user.setRole(request.getRole());
        if(!user.getEmail().equals(request.getEmail())){
            user.setEmail(request.getEmail());
        }
        user.setGender(request.getGender());
        repository.save(user);
        List<UserExportDTO> userExportDTOS = new ArrayList<>();
        userExportDTOS.add(userMapper.toUserExportDTO(user));
        sendEmailService.sendEmailToCreateUser(userExportDTOS);
        return mapper.toUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO createUser(RequestCreateUserDTO request) {
        if(repository.existsByEmail(request.getEmail())){
            throw new UserException(ErrorMessage.User.ERR_ALREADY_EXISTS_EMAIL+ "." + request.getEmail());
        }
        User user = mapper.toUser(request);
        long counter = userRepository.findMaxUserId();
        String[] parts = user.getFullName().trim().split("\\s+");
        String lastWord = parts[parts.length - 1];
        String username = VietnameseUtils.removeAccents(lastWord) + "hit" + counter;
        user.setUsername(username.toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(GenPassword.generatePassword()));
        user.setActivate(false);
        user.setCheckToken("a");
        user.setCreatedAt(LocalDate.now());
        user.setActivate(false);
        repository.save(user);
        List<User> users = new ArrayList<>();
        users.add(user);
        sendEmailService.sendEmailToCreateUser(userMapper.toListUserExportDTO(users));
        return mapper.toUserResponseDTO(user);
    }


    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USER_NAME));
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(()
        -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USER_ID));
    }

    @Override
    public ChangePasswordResponseDTO changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = checkToken();

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPasswordHash())){
            throw new BadRequestException(ErrorMessage.User.ERR_INVALID_PASSWORD);
        }

        if(passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPasswordHash())){
            throw new BadRequestException(ErrorMessage.User.ERR_SAME_PASSWORD);
        }

        user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setCheckToken("a");
        userRepository.save(user);
        return new ChangePasswordResponseDTO(SuccessMessage.User.PASSWORD_CHANGE_SUCCESS);
    }

    @Override
    public UserInfoResponseDTO getUserInfo() {
        User user = checkToken();
        return userMapper.toUserInforResponseDTO(user);
    }

    @Override
    public UserInfoResponseDTO updateUser(UpdateUserRequest updateUserRequest) {
        User user = checkToken();

        MultipartFile file = updateUserRequest.getAvatar();
        List<String> avatarUrls = new ArrayList<>();

        if (file != null && !file.isEmpty()) {
            try {
                avatarUrls = imageService.uploadImage(new MultipartFile[]{file}, TargetType.USER, user.getUserId());
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
        admin.setActivate(true);
        admin.setCheckToken("a");
        userRepository.save(admin);
        return true;
    }

    @Override
    public Map<String, Object> getAllUser(int page, int size) {
        User userIn = checkToken();
        Pageable pageable = PageRequest.of(page, size);
        Page<User> pageResult = userRepository.findAll(pageable);
        List<UserDetailResponseDTO> userDTOs = pageResult.getContent().stream()
                .filter(user -> !userIn.getUsername().equals(user.getUsername()))
                .map(user -> new UserDetailResponseDTO(
                        user.getUserId(),
                        user.getUsername(),
                        user.getRole(),
                        user.getGender(),
                        user.getDob(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getCreatedAt(),
                        user.getDeletedAt()
                ))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("items", userDTOs);
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());
        response.put("currentPage", pageResult.getNumber());
        response.put("pageSize", pageResult.getSize());

        return response;
    }


    @Override
    public List<UserExportDTO> getAllUserToSendEmail() {
        return userRepository.findAllForExport();
    }

    @Override
    public boolean resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO) {
        User user = findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.isActivate())
            throw new BadRequestException(ErrorMessage.User.ERR_ACTIVATED);
        if(passwordEncoder.matches(resetPasswordRequestDTO.getNewPassword(), user.getPasswordHash()))
            throw new BadRequestException(ErrorMessage.User.ERR_SAME_PASSWORD);
        user.setPasswordHash(passwordEncoder.encode(resetPasswordRequestDTO.getNewPassword()));
        user.setActivate(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetailResponseDTO deleteUser(String username) {
       User user = findUserByUsername(username);
       User userDelete = checkToken();
       if(userDelete.getRole().equals(Role.TV) || username.equals(userDelete.getUsername()))
           throw new BadRequestException(ErrorMessage.User.ERR_NOT_ENOUGH_RIGHTS);
       user.setDeletedAt(LocalDate.now());
       user.setActivate(false);
       user.setCheckToken("a");
       userRepository.save(user);
       return userMapper.toUserDetailResponseDTO(user);
    }

    @Override
    public UserDetailResponseDTO getUserDetailByAdmin(Long userId) {
        User user = findUserById(userId);
        return userMapper.toUserDetailResponseDTO(user);
    }

    @Override
    public UserResponseDTO restoreUser(RestoreUserRequestDTO restoreUserRequestDTO) {
        User user = userRepository.findByEmail(restoreUserRequestDTO.getEmail()).orElseThrow(()
        -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_EMAIL));
        user.setDeletedAt(null);
        user.setActivate(false);
        user.setCheckToken("a");
        userRepository.save(user);
        List<UserExportDTO> userExportDTOS = new ArrayList<>();
        userExportDTOS.add(userMapper.toUserExportDTO(user));
        sendEmailService.sendEmailToCreateUser(userExportDTOS);
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public void setActivated(User user) {
        user.setActivate(false);
        userRepository.save(user);
    }

    @Override
    public User checkToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = findUserByUsername(authentication.getName());
        if(!passwordEncoder.matches(
                JwtUtils.getTokenPass((String) authentication.getCredentials(), jwtProperties.getSecret())
                ,user.getCheckToken())){
            throw new ForbiddenException(ErrorMessage.User.ERR_INVALID_TOKEN);
        }
        return user;
    }

}

