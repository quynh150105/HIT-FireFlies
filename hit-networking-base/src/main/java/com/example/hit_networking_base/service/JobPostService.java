package com.example.hit_networking_base.service;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.JobPostRequest;
import com.example.hit_networking_base.domain.dto.request.JobUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.JobDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobPostResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobResponseDTO;
import com.example.hit_networking_base.domain.entity.JobPost;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import java.awt.*;
import java.util.List;

public interface JobPostService {
    JobResponseDTO createJob(JobPostRequest jobPostRequest);
    Page<JobPostResponseDTO> getAllJobPosts(int page, int size);
    JobDetailResponseDTO getJobDetail(Long postId);
    JobResponseDTO updateJob(Long postId, JobUpdateRequestDTO jobPostRequest);
    JobPost findById(Long id);
    JobPostResponseDTO getJobPost(Long id);
    void countComment(Long id, TargetType targetType);
    JobDetailResponseDTO deleteJob(Long id);
    Page<JobPostResponseDTO> getAllMyJobPosts(int page, int size);
}
