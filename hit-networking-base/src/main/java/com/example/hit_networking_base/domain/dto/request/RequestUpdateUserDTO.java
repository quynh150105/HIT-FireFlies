package com.example.hit_networking_base.domain.dto.request;

import com.example.hit_networking_base.constant.Gender;
import com.example.hit_networking_base.constant.Role;
import com.example.hit_networking_base.domain.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateUserDTO {
    @NotBlank(message="Hay nhap userName")
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    private String username;

    @Schema(description = "Password", example = "Admin1234")
    @NotBlank(message = "Password must not be blank")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Password must be at least 8 characters long, include at least one digit, one lowercase and one uppercase letter"
    )
    private String passwordHash;

    @NotBlank
    private Role role = Role.TV;

    private Gender gender = Gender.MALE;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải trước ngày hôm nay")
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotBlank(message="Hay nhap ho va ten")
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    private String fullName;

    @Email(message="Hay nhap email dung dinh dang")
    @NotBlank(message = "email khong duoc de trong")
    private String email;

}
