package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.exception.UserException;
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

    @Operation(summary = "BQT thêm người dùng theo form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thêm thành công",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "403", description = "Không có quyền thực hiện"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @PostMapping(UrlConstant.Admin.CREATE)
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestUpdateUserDTO request){
        return VsResponseUtil.success(userService.createUser(request));
    }


    @Operation(summary = "BQT import file Excel danh sách thành viên")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Import thành công",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "File không hợp lệ hoặc định dạng sai"),
            @ApiResponse(responseCode = "401", description = "Chưa đăng nhập"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @PostMapping(value = UrlConstant.Admin.IMPORT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importExcelFile(@ModelAttribute MultipartFile file){
        return VsResponseUtil.success(saveListAccountUser.saveListAccUsersToDatabase(file));
    }

    @Operation(summary = "BQT lấy toàn bộ thông tin danh sách thành viên")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa đăng nhập"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @GetMapping(UrlConstant.Admin.GET_ALL)
    public ResponseEntity<?> getAllUsers(){
        return  VsResponseUtil.success(saveListAccountUser.getAllUser());
    }

    @Operation(summary = "BQT cập nhật thông tin của thành viên")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "401", description = "Chưa đăng nhập"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @PutMapping(UrlConstant.Admin.UPDATE)
    public ResponseEntity<?> updateUser(@Valid @RequestBody RequestUpdateUserDTO request){
        return VsResponseUtil.success(userService.updateUser(request));
    }
}
