package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.response.JobDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobPostResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobResponseDTO;
import com.example.hit_networking_base.domain.entity.JobPost;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobPostMapper {
    JobResponseDTO toJobResponse(JobPost jobPost);
    JobPostResponseDTO toJobPostResponse(JobPost jobPosts);
    JobDetailResponseDTO toJonDetailResponse(JobPost jobPost);
}
