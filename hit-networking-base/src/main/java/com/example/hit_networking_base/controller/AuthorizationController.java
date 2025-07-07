package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.request.ResetPasswordRequest;
import com.example.hit_networking_base.domain.dto.response.AuthResponseDTO;
import com.example.hit_networking_base.service.AuthService;
import com.example.hit_networking_base.service.impl.JobPostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthService authService;
    private final JobPostServiceImpl jobPostService;

    @Operation(summary = "Đăng nhập")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Sai thông tin người dùng"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @PostMapping(UrlConstant.Authorization.LOGIN)
    public ResponseEntity<?> login(AuthRequest authRequest){
        return VsResponseUtil.success(authService.login(authRequest));
    }

    @Operation(summary = "Cấp lại mật khẩu (Quên mật khẩu)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gửi lại mật khẩu thành công",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @PostMapping(UrlConstant.Authorization.REST_PASSWORD)
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        return VsResponseUtil.success(authService.resetPassword(request));
    }

    @Operation(summary = "Tất cả bài đăng trên Trang chủ (Home)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)
                    )),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @GetMapping(UrlConstant.Authorization.HOME)
    public ResponseEntity<?> listJobPosts(){
        return VsResponseUtil.success(jobPostService.getAllJobPosts());
    }
}
