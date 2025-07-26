package com.example.hit_networking_base.domain.dto.request;

import com.example.hit_networking_base.constant.EmotionType;
import com.example.hit_networking_base.constant.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionRequestDTO {
    private Long userId;
    private Long targetId;
    private TargetType targetType;
    private EmotionType emotionType;
}
