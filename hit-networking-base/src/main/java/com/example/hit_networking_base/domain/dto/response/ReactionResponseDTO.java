package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionResponseDTO {
    private long userId;
    private EmotionType emotionType;
}
