package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.CommentCreateRequestDTO;
import com.example.hit_networking_base.domain.dto.request.CommentUpdateRequestDTO;
import com.example.hit_networking_base.service.CommentPostService;
import com.example.hit_networking_base.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(UrlConstant.Comment.CREATE)
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentCreateRequestDTO commentCreateRequestDTO){
        return VsResponseUtil.success(commentService.createComment(commentCreateRequestDTO));
    }

    @GetMapping(UrlConstant.Comment.GET_DETAIL)
    public ResponseEntity<?> getCommentDetail(@RequestParam Long id){
        return VsResponseUtil.success(commentService.getCommentDetail(id));
    }

    @PutMapping(UrlConstant.Comment.UPDATE)
    public ResponseEntity<?> updateComment(@RequestParam Long commentId, @RequestBody @Valid CommentUpdateRequestDTO commentUpdateRequestDTO){
        return VsResponseUtil.success(commentService.updateComment(commentId, commentUpdateRequestDTO));
    }

    @DeleteMapping(UrlConstant.Comment.DELETE)
    public ResponseEntity<?> deleteComment(@RequestParam Long id){
        return VsResponseUtil.success(commentService.deleteComment(id));
    }

}
