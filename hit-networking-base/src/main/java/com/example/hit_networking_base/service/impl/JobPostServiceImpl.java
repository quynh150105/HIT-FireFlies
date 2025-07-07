package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.domain.dto.response.JobPostResponse;
import com.example.hit_networking_base.domain.mapstruct.JobPostMapper;
import com.example.hit_networking_base.repository.JobPostRepository;
import com.example.hit_networking_base.service.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;
    private final JobPostMapper jobPostMapper;


    @Override
    public List<JobPostResponse> getAllJobPosts() {
        return jobPostMapper.toResponseList(jobPostRepository.findByDeletedAtIsNullOrderByCreatedAtDesc());
    }
}
