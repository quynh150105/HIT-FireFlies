package com.example.hit_networking_base.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExportDTO {
    private String username;
    private String password;
}
