package com.example.hit_networking_base.domain.dto.request;

import com.example.hit_networking_base.constant.Gender;
import com.example.hit_networking_base.constant.Role;
import com.example.hit_networking_base.domain.entity.*;
import com.example.hit_networking_base.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateUserDTO {
    @NotBlank(message="Hay nhap ho va ten")
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    @Schema(description = "full name", example = "Nguyễn Văn An")
    private String fullName;

    @NotNull
    @Schema(description = "role", example = "TV")
    private Role role;

    private Gender gender = Gender.MALE;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải trước ngày hôm nay")
    @JsonFormat( pattern = "yyyy-MM-dd")
    @Schema(description = "dob", example = "2000-01-20")
    private LocalDate dob;

    @Email(message="Hay nhap email dung dinh dang")
    @NotBlank(message = "email khong duoc de trong")
    @Schema(description = "email", example = "an@gmail.com")
    private String email;
}
