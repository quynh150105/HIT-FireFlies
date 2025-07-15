package com.example.hit_networking_base.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Schema(
            description = "List image",
            type = "array",
            implementation = MultipartFile.class
    )
    private MultipartFile[] urlImage;
}
