package com.example.hit_networking_base.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobUpdateRequestDTO {
    @NotBlank(message = "Not blank title")
    private String title;

    @NotBlank(message = "Not blank description")
    private String description;
}
