package com.example.hit_networking_base.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailResponseDTO {
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String organizer;
    private String location;
    private LocalDateTime createdAt;
    private UserPostResponseDTO creator;
    private List<String> images;
    private long countReaction;
    private long countComment;
    private List<ReactionResponseDTO> reactionResponseDTOS;
    private List<CommentResponseDTO> commentResponseDTOS;
}
