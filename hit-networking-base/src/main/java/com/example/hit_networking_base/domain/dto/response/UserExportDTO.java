package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExportDTO {
    private String fullName;
    private String username;
    private Role role;
    private String email;


}
