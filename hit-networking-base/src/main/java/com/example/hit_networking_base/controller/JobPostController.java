package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.JobPostRequest;
import com.example.hit_networking_base.domain.dto.request.JobUpdateRequestDTO;
import com.example.hit_networking_base.service.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostService jobPostService;

    @PostMapping(value = UrlConstant.JobPost.CREATE_JOB_POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createJob(@ModelAttribute @Valid JobPostRequest jobPostRequest){
        return VsResponseUtil.success(jobPostService.createJob(jobPostRequest));
    }

    @PutMapping(UrlConstant.JobPost.UPDATE_JOB)
    public ResponseEntity<?> updateJob(@RequestParam(defaultValue = "1") Long id, @RequestBody @Valid JobUpdateRequestDTO jobUpdateRequestDTO){
        return VsResponseUtil.success(jobPostService.updateJob(id,jobUpdateRequestDTO));
    }

    @GetMapping(UrlConstant.JobPost.GET_JOB_POST_PAGE)
    public ResponseEntity<?> getJobPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return VsResponseUtil.success(jobPostService.getAllJobPosts(page, size));
    }

    @GetMapping(UrlConstant.JobPost.GET_JOB_DETAIL)
    public ResponseEntity<?> getJobDetail(@RequestParam(defaultValue = "1") Long id){
        return VsResponseUtil.success(jobPostService.getJobDetail(id));
    }
}
