package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.Gender;
import com.example.hit_networking_base.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponseDTO {
    private Long userId;
    private String username;
    private Role role;
    private Gender gender;
    private LocalDate dob;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate createdAt;
    private LocalDate deletedAt;
}

