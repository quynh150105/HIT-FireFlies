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

    @NotBlank(message="Hãy nhập họ và tên")
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    private String fullName;

    @NotNull
    private Role role;

    @NotNull
    private Gender gender;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải trước ngày hôm nay")
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDate dob;


    @Email(message="Nhập email đúng định dạng")
    @NotBlank(message = "Email không hợp lệ")
    private String email;

}
