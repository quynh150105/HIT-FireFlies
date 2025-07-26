package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionListResponseDTO {
    private long total;

    private Map<EmotionType,Long> reactions;
}
