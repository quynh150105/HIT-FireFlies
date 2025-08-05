package com.example.hit_networking_base.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDetailResponseDTO {
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private UserPostResponseDTO creator;
    private List<String> images;
    private long countReaction;
    private long countComment;
    private long countCv;
    private List<ReactionResponseDTO> reactionResponseDTOS;
    private List<CommentResponseDTO> commentResponseDTOS;
    private List<CvApplyInforDTO> totalCvResponseDTOS;
    private boolean checkReaction;
    private boolean checkApply;

}
