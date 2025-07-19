package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.CommentCreateRequestDTO;
import com.example.hit_networking_base.domain.dto.request.CommentUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.CommentDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.domain.entity.Comment;

public interface CommentService {
    CommentDetailResponseDTO getCommentDetail(long commentId);
    CommentDetailResponseDTO deleteComment(long commentId);
    Comment findCommentById(long id);
    CommentResponseDTO updateComment(Long commentId, CommentUpdateRequestDTO commentUpdateRequestDTO);
    CommentResponseDTO createComment(CommentCreateRequestDTO commentCreateRequestDTO);
}
