package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.RequestCreateUserDTO;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.service.SaveListAccountUser;
import com.example.hit_networking_base.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestCreateUserDTO request){
        return VsResponseUtil.success(userService.createUser(request));
    }
    @Operation(
            summary = "Admin imports user list from Excel file",
            description = "Allows the admin to upload an Excel (.xlsx) file containing a list of users to be added to the system.\n\n" +
                    "**File format requirements:**\n" +
                    "- Format: `.xlsx` (Excel 2007 or newer)\n" +
                    "- Columns (in order):\n" +
                    "  1. Full Name\n" +
                    "  2. Gender (NAM, NỮ)\n" +
                    "  3. Date of Birth (`yyyy-MM-dd`)\n" +
                    "  4. Email\n\n" +
                    "**Notes:**\n" +
                    "- If the generated username already exists (and hasn't been soft-deleted) → throw error\n" +
                    "- Passwords are randomly generated\n" +
                    "- `.xls` files (older Excel format) are not supported"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File imported successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserExportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid or incorrect file format", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(value = UrlConstant.Admin.IMPORT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importExcelFile(
            @Parameter(description = "Excel file (.xlsx) List user ", required = true)
            @ModelAttribute MultipartFile file) {
        return VsResponseUtil.success(saveListAccountUser.saveListAccUsersToDatabase(file));
    }


    @Operation(summary = "Admin retrieves all user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Admin.GET_ALL)
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return VsResponseUtil.success(userService.getAllUser(page, size));
    }

    @Operation(summary = "Admin updates user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(UrlConstant.Admin.UPDATE)
    public ResponseEntity<?> updateUser(@RequestParam Long id, @Valid @RequestBody RequestUpdateUserDTO request){
        return VsResponseUtil.success(userService.updateUser(id, request));
    }

    @Operation(summary = "Admin delete user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(UrlConstant.Admin.DELETE)
    public ResponseEntity<?> deleteUser(@RequestParam String username){
        return VsResponseUtil.success(userService.deleteUser(username));
    }

    @Operation(summary = "Admin get user detail account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get user detail successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Admin.GET_DETAIL)
    public ResponseEntity<?> getUserDetail(@RequestParam Long userId){
        return VsResponseUtil.success(userService.getUserDetailByAdmin(userId));
    }

}
