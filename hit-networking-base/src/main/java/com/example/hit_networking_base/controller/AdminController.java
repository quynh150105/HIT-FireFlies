package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.service.SaveListAccountUser;
import com.example.hit_networking_base.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class AdminController {
    private final SaveListAccountUser saveListAccountUser;
    private final UserService userService;

    @Operation(summary = "Admin creates a new user via form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(UrlConstant.Admin.CREATE)
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestUpdateUserDTO request){
        return VsResponseUtil.success(userService.createUser(request));
    }

    @Operation(summary = "Admin imports user list from Excel file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File imported successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid or incorrect file format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = UrlConstant.Admin.IMPORT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importExcelFile(@ModelAttribute MultipartFile file){
        return VsResponseUtil.success(saveListAccountUser.saveListAccUsersToDatabase(file));
    }

    @Operation(summary = "Admin retrieves all user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Admin.GET_ALL)
    public ResponseEntity<?> getAllUsers(){
        return VsResponseUtil.success(saveListAccountUser.getAllUser());
    }

    @Operation(summary = "Admin updates user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(UrlConstant.Admin.UPDATE)
    public ResponseEntity<?> updateUser(@Valid @RequestBody RequestUpdateUserDTO request){
        return VsResponseUtil.success(userService.updateUser(request));
    }
}
