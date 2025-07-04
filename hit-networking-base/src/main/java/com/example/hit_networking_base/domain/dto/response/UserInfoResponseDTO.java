package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDTO {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String avatarUrl;
    private Gender gender;
    private LocalDate dob;
}
