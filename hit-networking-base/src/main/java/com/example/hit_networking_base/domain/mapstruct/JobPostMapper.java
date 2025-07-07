package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.response.JobPostResponse;
import com.example.hit_networking_base.domain.entity.JobPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobPostMapper {
    @Mapping(target = "creatorName", expression = "java(jobPost.getCreator() != null ? jobPost.getCreator().getFullName() : null)")
    JobPostResponse toResponse(JobPost jobPost);
    List<JobPostResponse> toResponseList(List<JobPost> jobPosts);
}

