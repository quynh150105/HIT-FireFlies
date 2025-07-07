package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.response.JobPostResponse;
import com.example.hit_networking_base.domain.mapstruct.JobPostMapper;
import com.example.hit_networking_base.repository.JobPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    private final JobPostMapper jobPostMapper;

    public JobPostService(JobPostRepository jobPostRepository, JobPostMapper jobPostMapper) {
        this.jobPostRepository = jobPostRepository;
        this.jobPostMapper = jobPostMapper;
    }

    public List<JobPostResponse> getAllJobPosts() {
        return jobPostMapper.toResponseList(jobPostRepository.findByDeletedAtIsNullOrderByCreatedAtDesc());
    }
}
