package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportResult {
    private List<User> users;

    private List<UserExportDTO> exportDTOS;

}
