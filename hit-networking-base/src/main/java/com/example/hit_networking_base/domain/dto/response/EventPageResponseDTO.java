package com.example.hit_networking_base.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPageResponseDTO {
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String organizer;
    private String location;
    private LocalDateTime createdAt;
    private List<String> images;
    private List<ReactionResponseDTO> reactionResponseDTOS;
    private List<CommentResponseDTO> commentResponseDTOS;
}
