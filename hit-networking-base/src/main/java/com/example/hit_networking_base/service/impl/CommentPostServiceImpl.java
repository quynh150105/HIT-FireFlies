package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.domain.mapstruct.CommentMapper;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.repository.CommentRepository;
import com.example.hit_networking_base.service.CommentPostService;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CommentPostServiceImpl implements CommentPostService {
    private final CommentRepository commentRepository;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    @Override
    public List<CommentResponseDTO> findCommentByTargetIdAndTargetType(long targetId, TargetType targetType) {
        return commentRepository.findByTargetIdAndTargetType(targetId, targetType).stream()
                .map(comment -> {
                    CommentResponseDTO commentResponseDTO = commentMapper.toCommentResponseDTO(comment);
                    commentResponseDTO.setUserPostResponseDTO(userMapper.toUserPostResponseDTO(comment.getUser()));
                    return commentResponseDTO;
                })
                .collect(Collectors.toList());
    }
}
