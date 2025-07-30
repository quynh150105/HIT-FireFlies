package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostResponseDTO {
    private long postId;
    private String title;
    private String description;
    private UserPostResponseDTO creator;
    private List<String> urlImage;
    private long countReaction;
    private long countComment;
    private LocalDateTime createdAt;
}