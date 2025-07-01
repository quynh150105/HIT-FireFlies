package com.example.hit_networking_base.domain.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobPostResponse {
    private Integer postId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String creatorName;
}