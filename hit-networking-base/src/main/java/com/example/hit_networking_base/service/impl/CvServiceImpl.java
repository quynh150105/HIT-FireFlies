package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.domain.dto.request.RequestCvDTO;
import com.example.hit_networking_base.domain.dto.response.CvResponseDTO;
import com.example.hit_networking_base.domain.entity.CV;
import com.example.hit_networking_base.domain.entity.JobPost;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.repository.CvRepository;
import com.example.hit_networking_base.repository.JobPostRepository;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {
    private final CvRepository cvRepository;
    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;

    @Override
    public CvResponseDTO createCv(RequestCvDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        JobPost post = jobPostRepository.findByPostIdAndDeletedAtIsNull(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        CV cv = new CV();
        cv.setApplyDate(LocalDateTime.now());
        cv.setLinkCV(dto.getLinkCV());
        cv.setUser(user);
        cv.setJobPost(post);

        CV savedCv = cvRepository.save(cv);

        return CvResponseDTO.builder()
                .id(savedCv.getId())
                .linkCV(savedCv.getLinkCV())
                .applyDate(savedCv.getApplyDate())
                .userId(user.getUserId())
                .postId(post.getPostId())
                .build();
    }

    @Override
    public List<CvResponseDTO> getCVsByPostId(Long postId) {
        return cvRepository.findByJobPostIdAndDeletedAtIsNull(postId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CvResponseDTO> getCVsByUserId(Long userId) {
        return cvRepository.findByUserIdAndDeletedAtIsNull(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CvResponseDTO convertToDTO(CV cv) {
        return CvResponseDTO.builder()
                .id(cv.getId())
                .linkCV(cv.getLinkCV())
                .applyDate(cv.getApplyDate())
                .userId(cv.getUser() != null ? cv.getUser().getUserId() : null)
                .postId(cv.getJobPost() != null ? cv.getJobPost().getPostId(): null)
                .build();
    }
    @Override
    public void applyToJob(Long userId, Long postId, String linkCV) {
        if (cvRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalStateException("Bạn đã apply bài viết này rồi.");
        }

        CV cv = new CV();
        cv.setUserId(userId);
        cv.setPostId(postId);
        cv.setLinkCV(linkCV);
        cv.setApplyDate(LocalDateTime.now());

        cvRepository.save(cv);
    }

    @Override
    public boolean hasUserApplied(Long userId, Long postId) {
        return cvRepository.existsByUserIdAndPostId(userId, postId);
    }
}
