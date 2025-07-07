package com.example.hit_networking_base.service;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    List<CommentResponseDTO> findCommentByTargetIdAndTargetType(long targetId, TargetType targetType);
}
