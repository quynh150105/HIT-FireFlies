package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.domain.dto.response.JobPostResponse;
import com.example.hit_networking_base.service.JobPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobposts")
public class JobPostController {

    private final JobPostService jobPostService;

    public JobPostController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    @GetMapping
    public List<JobPostResponse> listJobPosts() {
        return jobPostService.getAllJobPosts();
    }
}
