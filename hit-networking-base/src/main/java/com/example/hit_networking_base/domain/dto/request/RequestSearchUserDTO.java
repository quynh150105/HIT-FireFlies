package com.example.hit_networking_base.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSearchUserDTO {
    @NotBlank(message="userName khong duoc de trong")
    private String username;

    @NotBlank(message="fullName khong duoc de trong")
    private String fullName;

    @Email(message="vui long nhap dung dinh dang email")
    private String email;

}
