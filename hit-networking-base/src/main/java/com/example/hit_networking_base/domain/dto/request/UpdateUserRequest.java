package com.example.hit_networking_base.domain.dto.request;

import com.example.hit_networking_base.constant.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    private MultipartFile avatar;

    @NotNull
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Past(message = "Ngay sinh phai trong qua khu")
    private LocalDate dob;
}
