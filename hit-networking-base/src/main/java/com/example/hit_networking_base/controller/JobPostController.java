package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.JobPostRequest;
import com.example.hit_networking_base.domain.dto.request.JobUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.CommentResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobPostResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobResponseDTO;
import com.example.hit_networking_base.service.JobPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostService jobPostService;


    @Operation(summary = "User create job post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User create successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = UrlConstant.JobPost.CREATE_JOB_POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createJob(@ModelAttribute @Valid JobPostRequest jobPostRequest){
        return VsResponseUtil.success(jobPostService.createJob(jobPostRequest));
    }

    @Operation(summary = "User update his job post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User update successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(UrlConstant.JobPost.UPDATE_JOB)
    public ResponseEntity<?> updateJob(@RequestParam(defaultValue = "1") Long id, @RequestBody @Valid JobUpdateRequestDTO jobUpdateRequestDTO){
        return VsResponseUtil.success(jobPostService.updateJob(id,jobUpdateRequestDTO));
    }

    @Operation(summary = "Get list job post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list job post successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobPostResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.JobPost.GET_JOB_POST_PAGE)
    public ResponseEntity<?> getJobPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return VsResponseUtil.success(jobPostService.getAllJobPosts(page, size));
    }

    @Operation(summary = "User get job post detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User get successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.JobPost.GET_JOB_DETAIL)
    public ResponseEntity<?> getJobDetail(@RequestParam(defaultValue = "1") Long id){
        return VsResponseUtil.success(jobPostService.getJobDetail(id));
    }

    @Operation(summary = "User delete job post detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(UrlConstant.JobPost.DELETE)
    public ResponseEntity<?> deleteJob(@RequestParam Long id){
        return VsResponseUtil.success(jobPostService.deleteJob(id));
    }
}
