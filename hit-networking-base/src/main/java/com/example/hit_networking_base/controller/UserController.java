package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.example.hit_networking_base.domain.dto.request.ChangePasswordRequest;
import com.example.hit_networking_base.domain.dto.request.UpdateUserRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping(UrlConstant.Admin.UPDATE)
    public ResponseEntity<?> updateUser(@Valid @RequestBody RequestUpdateUserDTO request){
        return VsResponseUtil.success(userService.updateUser(request));
    }

    @PostMapping(UrlConstant.Admin.CREATE)
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestUpdateUserDTO request){
        return VsResponseUtil.success(userService.createUser(request));
    }
    @PutMapping(UrlConstant.User.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest){
        return VsResponseUtil.success(userService.changePassword(changePasswordRequest));
    }

    @GetMapping(UrlConstant.User.USER_INFO)
    public ResponseEntity<?> getInfo(){
        return VsResponseUtil.success(userService.getUserInfo());
    }

    @PutMapping(value = UrlConstant.User.USER_UPDATE_INFO, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(@ModelAttribute @Valid UpdateUserRequest updateUserRequest){
        return VsResponseUtil.success(userService.updateUser(updateUserRequest));
    }

    @GetMapping(UrlConstant.Admin.GETALL)
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "11") int size){
        return  VsResponseUtil.success(userService.getAllUser(page, size));
    }

}
