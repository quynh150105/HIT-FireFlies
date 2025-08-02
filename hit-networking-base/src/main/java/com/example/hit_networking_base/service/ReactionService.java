package com.example.hit_networking_base.service;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.ReactionRequestDTO;
import com.example.hit_networking_base.domain.dto.response.ReactionListResponseDTO;
import com.example.hit_networking_base.domain.dto.response.ReactionResponseDTO;

import java.util.List;

public interface ReactionService {
    List<ReactionResponseDTO> findReactionByTargetIdAndTargetType(long targetId, TargetType targetType);

    String createReaction(ReactionRequestDTO requestDTO);

    String updateReaction(ReactionRequestDTO requestDTO);

    String removeReaction(Long targetId, TargetType targetType);

    ReactionListResponseDTO getReaction(Long targetId, TargetType targetType);

    boolean hasUserReacted(Long targetID, TargetType targetType);

}
