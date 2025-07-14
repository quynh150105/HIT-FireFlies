package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.domain.entity.Comment;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.CommentMapper;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.repository.CommentRepository;
import com.example.hit_networking_base.service.CommentService;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserMapper userMapper;

    @Override
    public List<CommentResponseDTO> findCommentByTargetIdAndTargetType(long targetId, TargetType targetType) {
        return commentRepository.findByTargetIdAndTargetType(targetId, targetType).stream()
                .map(comment -> {
                    CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
                    commentResponseDTO.setContent(comment.getContent());
                    commentResponseDTO.setCreatedAt(comment.getCreatedAt());
                    commentResponseDTO.setUserPostResponseDTO(userMapper.toUserPostResponseDTO(comment.getUser()));
                    return commentResponseDTO;
                })
                .collect(Collectors.toList());
    }
}
