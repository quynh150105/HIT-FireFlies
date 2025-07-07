package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.Gender;
import com.example.hit_networking_base.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Integer userId;

    private String fullName;

    private Gender gender;

    private LocalDate dob;

    private String email;

    private String username;

    private String passwordHash;


}
