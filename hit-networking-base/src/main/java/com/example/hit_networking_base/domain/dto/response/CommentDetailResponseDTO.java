package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailResponseDTO {
    private long commentId;
    private String content;
    private TargetType targetType;
    private long targetId;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private UserPostResponseDTO userPostResponseDTO;
}
