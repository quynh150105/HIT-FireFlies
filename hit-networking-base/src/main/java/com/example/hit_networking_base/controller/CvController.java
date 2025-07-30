package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.CVCreateRequestDTO;
import com.example.hit_networking_base.domain.dto.request.CVUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.CvResponseDTO;
import com.example.hit_networking_base.service.CvService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CvController {
    private final CvService cVService;

    @Operation(summary = "User upload cv")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User upload successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CvResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = UrlConstant.Cv.UP_CV, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCv(@ModelAttribute @Valid CVCreateRequestDTO cvCreateRequestDTO) {
        return VsResponseUtil.success(cVService.createCv(cvCreateRequestDTO));
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
    public ResponseEntity<?> getCvsByPostId(
            @RequestParam Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return VsResponseUtil.success(cVService.getCVsByPostId(postId, page, size));
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
    public ResponseEntity<?> getCvsByUserId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return VsResponseUtil.success(cVService.getMyCVS(page, size));
    }

    @Operation(summary = "Delete cv by cvId")
    @DeleteMapping(UrlConstant.Cv.DELETE)
    public ResponseEntity<?> deleteCV(@RequestParam Long cvId){
        return VsResponseUtil.success(cVService.deleteCV(cvId));
    }

    @Operation(summary = "Update cv by cvId")
    @PutMapping(value = UrlConstant.Cv.UPDATE , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCV(
            @RequestParam Long cvId,
            @ModelAttribute @Valid CVUpdateRequestDTO cvUpdateRequestDTO){
        return VsResponseUtil.success(cVService.updateCV(cvId, cvUpdateRequestDTO));
    }

    @Operation(summary = "Download list cv in my post")
    @GetMapping(UrlConstant.Cv.DOWNLOAD)
    public void downloadCV(@RequestParam Long postId, HttpServletResponse httpServletResponse){
        cVService.downloadCV(postId, httpServletResponse);
    }
}
