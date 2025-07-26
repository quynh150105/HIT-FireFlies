package com.example.hit_networking_base.domain.dto.request;

import com.example.hit_networking_base.constant.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequestDTO {
    @NotNull
    private long targetId;
    @NotNull
    private TargetType targetType;
    @NotBlank
    private String content;
}
