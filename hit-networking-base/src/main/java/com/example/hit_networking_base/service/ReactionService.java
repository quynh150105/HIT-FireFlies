package com.example.hit_networking_base.service;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.response.ReactionResponseDTO;

import java.util.List;

public interface ReactionService {
    List<ReactionResponseDTO> findReactionByTargetIdAndTargetType(long targetId, TargetType targetType);
}
