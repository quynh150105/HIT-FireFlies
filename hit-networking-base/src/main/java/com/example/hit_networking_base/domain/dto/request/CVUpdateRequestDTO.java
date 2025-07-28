package com.example.hit_networking_base.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CVUpdateRequestDTO {
    @NotNull(message = "Not null file CV")
    private MultipartFile file;
}
