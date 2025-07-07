package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.domain.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponseDTO toCommentResponseDTO(Comment comment);
}
