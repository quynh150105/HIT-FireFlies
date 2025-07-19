package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.response.ReactionResponseDTO;
import com.example.hit_networking_base.domain.entity.Reaction;
import com.example.hit_networking_base.domain.mapstruct.ReactionMapper;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.repository.ReactionRepository;
import com.example.hit_networking_base.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository repository;
    private final UserMapper userMapper;

    @Override
    public List<ReactionResponseDTO> findReactionByTargetIdAndTargetType(long targetId, TargetType targetType) {
        return repository.findByTargetIdAndTargetType(targetId, targetType).stream()
                .map(reaction -> {
                    ReactionResponseDTO reactionResponseDTO = new ReactionResponseDTO();
                    reactionResponseDTO.setEmotionType(reaction.getEmotionType());
                    reactionResponseDTO.setCreatedAt(reaction.getCreatedAt());
                    reactionResponseDTO.setUserPostResponseDTO(userMapper.toUserPostResponseDTO(reaction.getUser()));
                    return reactionResponseDTO;
                })
                .collect(Collectors.toList());
    }

}
