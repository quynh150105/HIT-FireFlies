package com.example.hit_networking_base.service;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.CommentCreateRequestDTO;
import com.example.hit_networking_base.domain.dto.request.CommentUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.CommentDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.domain.entity.Comment;

import java.util.List;

public interface CommentPostService {
    List<CommentResponseDTO> findCommentByTargetIdAndTargetType(long targetId, TargetType targetType);
}
