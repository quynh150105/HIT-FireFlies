package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.ApplyRequest;
import com.example.hit_networking_base.domain.dto.request.RequestCvDTO;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.domain.dto.response.CvResponseDTO;
import com.example.hit_networking_base.repository.CvRepository;
import com.example.hit_networking_base.service.CvService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestApiV1
@RequiredArgsConstructor
public class CvController {
    private final CvService service;

    @Operation(summary = "User upload cv")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User upload successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CvResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(UrlConstant.Cv.UP_CV)
    public Object createCv(@RequestBody RequestCvDTO dto) {
        CvResponseDTO response = service.createCv(dto);
        return VsResponseUtil.success(response);
    }

    @Operation(summary = "User get all by post  cv")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User get all successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CvResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Cv.GET_BY_POST)
    public Object getCvsByPostId(@RequestParam Long postId) {
        List<CvResponseDTO> cvs = service.getCVsByPostId(postId);
        return VsResponseUtil.success(cvs);
    }

    @Operation(summary = "User get all by user id  cv")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User get all successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CvResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Cv.GET_BY_USERID)
    public Object getCvsByUserId(@RequestParam Long userId) {
        List<CvResponseDTO> cvs = service.getCVsByUserId(userId);
        return VsResponseUtil.success(cvs);
    }

    @PostMapping(UrlConstant.Cv.APPLY)
    public ResponseEntity<?> applyJob(@RequestBody ApplyRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        service.applyToJob(userId, request.getPostId(), request.getLinkCV());
        return ResponseEntity.ok("Bạn đã apply thành công");
    }

    @GetMapping(UrlConstant.Cv.CHECK_APPLIED)
    public ResponseEntity<?> checkApplied(@RequestParam Long postId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        boolean applied = service.hasUserApplied(userId, postId);
        return ResponseEntity.ok(applied);
    }

}
