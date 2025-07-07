package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.response.JobPostResponse;

import java.util.List;

public interface JobPostService {
    List<JobPostResponse> getAllJobPosts();
}
