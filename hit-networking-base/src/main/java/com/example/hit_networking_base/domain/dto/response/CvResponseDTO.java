package com.example.hit_networking_base.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CvResponseDTO {
    private Long id;
    private String linkCV;
    private LocalDateTime applyDate;
    private Long userId;
    private Long postId;
}
