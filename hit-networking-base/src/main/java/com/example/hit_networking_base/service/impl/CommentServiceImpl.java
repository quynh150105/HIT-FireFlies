package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.Role;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.CommentCreateRequestDTO;
import com.example.hit_networking_base.domain.dto.request.CommentUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.CommentDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.domain.entity.Comment;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.CommentMapper;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.repository.CommentRepository;
import com.example.hit_networking_base.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final CommentMapper commentMapper;
    private final JobPostService jobPostService;
    private final EventService eventService;

    @Override
    public CommentDetailResponseDTO getCommentDetail(long commentId) {
        Comment comment = findCommentById(commentId);
        CommentDetailResponseDTO commentDetailResponseDTO = commentMapper.toCommentDetailDTO(comment);
        commentDetailResponseDTO.setUserPostResponseDTO(userMapper.toUserPostResponseDTO(comment.getUser()));
        return commentDetailResponseDTO;
    }

    @Override
    public CommentDetailResponseDTO deleteComment(long commentId) {
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Comment comment = findCommentById(commentId);
        if(!user.equals(comment.getUser()) && user.getRole().equals(Role.TV)){
            throw new BadRequestException(ErrorMessage.Comment.ERR_NOT_ENOUGH_RIGHTS);
        }
        comment.setDeletedAt(LocalDateTime.now());

        if(comment.getTargetType().equals(TargetType.JOB)){
            jobPostService.countComment(comment.getTargetId(), TargetType.DELETE);
        } else {
            eventService.countComment(comment.getTargetId(), TargetType.DELETE);
        }
        commentRepository.save(comment);
        CommentDetailResponseDTO commentDetailResponseDTO = commentMapper.toCommentDetailDTO(comment);
        commentDetailResponseDTO.setUserPostResponseDTO(userMapper.toUserPostResponseDTO(comment.getUser()));
        return commentDetailResponseDTO;
    }

    @Override
    public Comment findCommentById(long id) {
        return commentRepository.findByCommentIdAndDeletedAtIsNull(id).orElseThrow(()
        -> new NotFoundException(ErrorMessage.Comment.ERR_NOT_FOUND_COMMENT));
    }

    @Override
    public CommentResponseDTO updateComment(Long commentId, CommentUpdateRequestDTO commentUpdateRequestDTO) {
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Comment comment = findCommentById(commentId);
        if(!user.equals(comment.getUser()))
            throw new BadRequestException(ErrorMessage.Comment.ERR_NOT_ENOUGH_RIGHTS);
        comment.setContent(commentUpdateRequestDTO.getContent());
        commentRepository.save(comment);
        return commentMapper.toCommentResponseDTO(comment);
    }

    @Override
    public CommentResponseDTO createComment(CommentCreateRequestDTO commentCreateRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        Comment comment = commentMapper.toComment(commentCreateRequestDTO);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        if(comment.getTargetType().equals(TargetType.JOB)){
            jobPostService.countComment(comment.getTargetId(), TargetType.CREATE);
        } else eventService.countComment(comment.getTargetId(), TargetType.CREATE);
        commentRepository.save(comment);
        return commentMapper.toCommentResponseDTO(comment);
    }

}
