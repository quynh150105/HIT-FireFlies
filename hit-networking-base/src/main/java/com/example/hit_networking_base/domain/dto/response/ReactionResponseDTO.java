package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionResponseDTO {
    private long id;
    private UserPostResponseDTO userPostResponseDTO;
    private EmotionType emotionType;
    private LocalDateTime createdAt;
}
