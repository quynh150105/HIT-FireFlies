package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.response.ReactionResponseDTO;
import com.example.hit_networking_base.domain.entity.Reaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReactionMapper {
    ReactionResponseDTO toReactionResponseDTO(Reaction reaction);
}
