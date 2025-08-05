package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.Role;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.JobPostRequest;
import com.example.hit_networking_base.domain.dto.request.JobUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.JobDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobPostResponseDTO;
import com.example.hit_networking_base.domain.dto.response.JobResponseDTO;
import com.example.hit_networking_base.domain.entity.JobPost;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.JobPostMapper;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.exception.LoadFileException;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.repository.CvRepository;
import com.example.hit_networking_base.repository.JobPostRepository;
import com.example.hit_networking_base.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;
    private final JobPostMapper jobMapper;
    private final CommentPostService commentPostService;
    private final ReactionService reactionService;
    private final ImageService imageService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final SendEmailService sendEmailService;
    private final CheckListService checkListService;
    private final CvRepository cvRepository;


    @Override
    public JobResponseDTO createJob(JobPostRequest jobPostRequest) {
        testInput(jobPostRequest.getTitle(), jobPostRequest.getDescription());
        JobPost jobPost = new JobPost();
        jobPost.setTitle(jobPostRequest.getTitle());
        jobPost.setDescription(jobPostRequest.getDescription());
        jobPost.setCreatedAt(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        jobPost.setCreator(userService.findUserByUsername(authentication.getName()));
        jobPost.setCountComment(0L);
        jobPost.setCountReaction(0L);
        jobPostRepository.save(jobPost);
        List<String>  listUrlImage = new ArrayList<>();
        MultipartFile[] files = jobPostRequest.getUrlImage();
        if (files != null) {
            List<MultipartFile> validFiles = Arrays.stream(files)
                    .filter(file -> file != null && !file.isEmpty() && file.getSize() > 0)
                    .collect(Collectors.toList());
            if(!validFiles.isEmpty()){
                try {
                    listUrlImage = imageService.uploadImage(
                            validFiles.toArray(new MultipartFile[0]), TargetType.JOB, jobPost.getPostId());
                } catch (IOException e) {
                    throw new LoadFileException(ErrorMessage.Image.ERR_UPLOAD);
                }
            }
        }
        JobResponseDTO jobResponse = jobMapper.toJobResponse(jobPost);
        jobResponse.setUrlImage(listUrlImage);

        sendEmailService.sendEmailWhenCreatePost(userService.getAllUserToSendEmail());

        return jobResponse;
    }

    @Override
    public Page<JobPostResponseDTO> getAllJobPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<JobPost> jobPostPage = jobPostRepository.findByDeletedAtIsNull(pageable);
        List<JobPostResponseDTO> jobPostResponseDTOS = jobPostPage.getContent().stream()
                .map(jobPost -> {
                    JobPostResponseDTO jobPostResponseDTO = jobMapper.toJobPostResponse(jobPost);
                    jobPostResponseDTO.setCreator(userMapper.toUserPostResponseDTO(jobPost.getCreator()));
                    jobPostResponseDTO.setUrlImage(imageService.getUrlImage(jobPost.getPostId(), TargetType.JOB));
                    return jobPostResponseDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(jobPostResponseDTOS, pageable, jobPostPage.getTotalElements());
    }

    @Override
    public JobDetailResponseDTO getJobDetail(Long postId) {
        User user = userService.checkToken();
        JobPost jobPost = findById(postId);
        JobDetailResponseDTO jobDetailResponseDTO = jobMapper.toJonDetailResponse(jobPost);
        jobDetailResponseDTO.setImages(imageService.getUrlImage(postId, TargetType.JOB));
        jobDetailResponseDTO.setCommentResponseDTOS(commentPostService.findCommentByTargetIdAndTargetType(postId, TargetType.JOB));
        jobDetailResponseDTO.setReactionResponseDTOS(reactionService.findReactionByTargetIdAndTargetType(postId, TargetType.JOB));
        jobDetailResponseDTO.setCheckReaction(reactionService.hasUserReacted(postId, TargetType.JOB));
        jobDetailResponseDTO.setCountCv(cvRepository.countByJobPostIdAndDeletedAtIsNull(postId));
        jobDetailResponseDTO.setTotalCvResponseDTOS(cvRepository.findCvApplyInfoByPostId(postId));
        jobDetailResponseDTO.setCheckApply(cvRepository.hasUserAppliedToPost(user.getUserId(),postId));
        return jobDetailResponseDTO;
    }

    @Override
    public JobResponseDTO updateJob(Long postId, JobUpdateRequestDTO jobPostRequest) {
        testInput(jobPostRequest.getTitle(), jobPostRequest.getDescription());
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        JobPost jobPost = findById(postId);
        if(!user.equals(jobPost.getCreator()))
            throw new BadRequestException(ErrorMessage.Job.ERR_NOT_ENOUGH_RIGHTS);
        jobPost.setUpdatedAt(LocalDateTime.now());
        jobPost.setTitle(jobPostRequest.getTitle());
        jobPost.setDescription(jobPostRequest.getDescription());
        jobPostRepository.save(jobPost);
        JobResponseDTO jobResponseDTO = jobMapper.toJobResponse(jobPost);
        jobResponseDTO.setUrlImage(imageService.getUrlImage(postId, TargetType.JOB));
        return jobResponseDTO;
    }

    @Override
    public JobPost findById(Long id) {
        return jobPostRepository.findByPostIdAndDeletedAtIsNull(id).orElseThrow(()
        -> new NotFoundException(ErrorMessage.Job.ERR_NOT_FOUND_JOB_ID));
    }

    @Override
    public JobPostResponseDTO getJobPost(Long id) {
        JobPost jobPost = findById(id);
        JobPostResponseDTO jobPostResponseDTO = jobMapper.toJobPostResponse(jobPost);
        jobPostResponseDTO.setUrlImage(imageService.getUrlImage(id, TargetType.JOB));
        jobPostResponseDTO.setCreator(userMapper.toUserPostResponseDTO(jobPost.getCreator()));
        jobPostResponseDTO.setCheckReaction(reactionService.hasUserReacted(jobPost.getPostId(), TargetType.JOB));
        return jobPostResponseDTO;
    }

    @Override
    public void countComment(Long id, TargetType targetType) {
        JobPost jobPost = findById(id);
        if(targetType.equals(TargetType.CREATE)){
            jobPost.setCountComment(jobPost.getCountComment() + 1);
        } else {
            if(jobPost.getCountComment()>0)
                jobPost.setCountComment(jobPost.getCountComment() - 1);
        }
        jobPostRepository.save(jobPost);
    }

    @Override
    public JobDetailResponseDTO deleteJob(Long id) {
        JobPost jobPost = findById(id);
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.getRole().equals(Role.TV) && !user.equals(jobPost.getCreator())) {
            throw new BadRequestException(ErrorMessage.User.ERR_NOT_ENOUGH_RIGHTS);
        }

        jobPost.setDeletedAt(LocalDateTime.now());
        jobPostRepository.save(jobPost);
        return jobMapper.toJonDetailResponse(jobPost);
    }

    @Override
    public Page<JobPostResponseDTO> getAllMyJobPosts(int page, int size) {
        User user = userService.checkToken();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<JobPost> jobPostPage = jobPostRepository.findByCreator_UserIdAndDeletedAtIsNull(user.getUserId(), pageable);
        List<JobPostResponseDTO> jobPostResponseDTOS = jobPostPage.getContent().stream()
                .map(jobPost -> {
                    JobPostResponseDTO jobPostResponseDTO = jobMapper.toJobPostResponse(jobPost);
                    jobPostResponseDTO.setCreator(userMapper.toUserPostResponseDTO(jobPost.getCreator()));
                    jobPostResponseDTO.setUrlImage(imageService.getUrlImage(jobPost.getPostId(), TargetType.JOB));
                    return jobPostResponseDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(jobPostResponseDTOS, pageable, jobPostPage.getTotalElements());
    }

    private void testInput(String title, String description){
        Map<String, String> fields = Map.of(
                "description", description,
                "title", title
        );
        checkListService.checkListText(fields);
    }
}
