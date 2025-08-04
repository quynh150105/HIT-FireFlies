package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.response.EventPostResponseDTO;
import com.example.hit_networking_base.domain.dto.response.HomeProjection;
import com.example.hit_networking_base.domain.dto.response.HomeResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobPostResponseDTO;
import com.example.hit_networking_base.repository.HomeRepository;
import com.example.hit_networking_base.service.EventService;
import com.example.hit_networking_base.service.HomeService;
import com.example.hit_networking_base.service.JobPostService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final EventService eventService;
    private final JobPostService jobPostService;
    private final HomeRepository homeRepository;


    @Override
    public Page<HomeResponseDTO> getALLEventAndJobPost(int page, int size) {
        List<Object[]> homeDTOS = homeRepository.getMergedPosts(size, page*size);
        long totalElements = homeRepository.countMergedPosts();
        List<HomeProjection> homeProjectionDTOS = homeDTOS.stream()
                .map(HomeProjection::new).collect(Collectors.toList());

        List<HomeResponseDTO> listHomeResponseDTOS = new ArrayList<>();
        for(HomeProjection homeProjection : homeProjectionDTOS){
            if(homeProjection.getTargetType().equals(TargetType.EVENT)){
                listHomeResponseDTOS.add(mergeEventToHome(eventService.getPostEvent(homeProjection.getPostId())));
            } else
                listHomeResponseDTOS.add((mergeJobToHome(jobPostService.getJobPost(homeProjection.getPostId()))));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        return new PageImpl<>(listHomeResponseDTOS, pageable, totalElements);
    }

    private HomeResponseDTO mergeEventToHome(EventPostResponseDTO eventPostResponseDTO){
        HomeResponseDTO homeResponseDTO = new HomeResponseDTO();
        homeResponseDTO.setPostId(eventPostResponseDTO.getEventId());
        homeResponseDTO.setTitle(eventPostResponseDTO.getTitle());
        homeResponseDTO.setDescription(eventPostResponseDTO.getDescription());
        homeResponseDTO.setCreator(eventPostResponseDTO.getCreator());
        homeResponseDTO.setCountComment(eventPostResponseDTO.getCountComment());
        homeResponseDTO.setCountReaction(eventPostResponseDTO.getCountReaction());
        homeResponseDTO.setUrlImage(eventPostResponseDTO.getUrlImage());
        homeResponseDTO.setCreatedAt(eventPostResponseDTO.getCreatedAt());
        homeResponseDTO.setTargetType(TargetType.EVENT);
        return homeResponseDTO;
    }
    private HomeResponseDTO mergeJobToHome(JobPostResponseDTO jobPostResponseDTO){
        HomeResponseDTO homeResponseDTO = new HomeResponseDTO();
        homeResponseDTO.setPostId(jobPostResponseDTO.getPostId());
        homeResponseDTO.setTitle(jobPostResponseDTO.getTitle());
        homeResponseDTO.setDescription(jobPostResponseDTO.getDescription());
        homeResponseDTO.setCreator(jobPostResponseDTO.getCreator());
        homeResponseDTO.setCountComment(jobPostResponseDTO.getCountComment());
        homeResponseDTO.setCountReaction(jobPostResponseDTO.getCountReaction());
        homeResponseDTO.setUrlImage(jobPostResponseDTO.getUrlImage());
        homeResponseDTO.setCreatedAt(jobPostResponseDTO.getCreatedAt());
        homeResponseDTO.setTargetType(TargetType.JOB);
        return homeResponseDTO;
    }
}
