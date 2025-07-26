package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.request.ResetPasswordRequest;
import com.example.hit_networking_base.domain.dto.response.AuthResponseDTO;
import com.example.hit_networking_base.domain.dto.response.HomeResponseDTO;
import com.example.hit_networking_base.service.AuthService;
import com.example.hit_networking_base.service.HomeService;
import com.example.hit_networking_base.service.impl.JobPostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthService authService;
    private final HomeService homeService;

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(UrlConstant.Authorization.LOGIN)
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest, HttpServletResponse response){
        return VsResponseUtil.success(authService.login(authRequest, response));
    }

    @Operation(summary = "Reset password (Forgot password)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset link sent successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(UrlConstant.Authorization.REST_PASSWORD)
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        return VsResponseUtil.success(authService.resetPassword(request));
    }

    @Operation(summary = "Get all job and event on the homepage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job post list retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HomeResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Authorization.HOME)
    public ResponseEntity<?> home(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return VsResponseUtil.success(homeService.getALLEventAndJobPost(page, size));
    }

    @Operation(summary = "Generate token when token is invalid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generate token successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Authorization.REFRESH)
    public ResponseEntity<?> refresh(HttpServletRequest request){
        return VsResponseUtil.success(authService.refreshToken(request));
    }
}
