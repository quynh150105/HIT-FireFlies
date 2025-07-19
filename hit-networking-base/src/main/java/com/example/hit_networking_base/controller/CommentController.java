package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.CommentCreateRequestDTO;
import com.example.hit_networking_base.domain.dto.request.CommentUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.CommentDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "User create comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User create successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(UrlConstant.Comment.CREATE)
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentCreateRequestDTO commentCreateRequestDTO){
        return VsResponseUtil.success(commentService.createComment(commentCreateRequestDTO));
    }

    @Operation(summary = "Admin get comment detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin get successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Comment.GET_DETAIL)
    public ResponseEntity<?> getCommentDetail(@RequestParam Long id){
        return VsResponseUtil.success(commentService.getCommentDetail(id));
    }

    @Operation(summary = "User update comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User update successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(UrlConstant.Comment.UPDATE)
    public ResponseEntity<?> updateComment(@RequestParam Long commentId, @RequestBody @Valid CommentUpdateRequestDTO commentUpdateRequestDTO){
        return VsResponseUtil.success(commentService.updateComment(commentId, commentUpdateRequestDTO));
    }


    @Operation(summary = "User deletes his comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User delete successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(UrlConstant.Comment.DELETE)
    public ResponseEntity<?> deleteComment(@RequestParam Long id){
        return VsResponseUtil.success(commentService.deleteComment(id));
    }

}
