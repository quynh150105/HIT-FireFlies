package com.example.hit_networking_base.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.hit_networking_base.config.CloudinaryConfig;
import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.Role;
import com.example.hit_networking_base.domain.dto.request.CVCreateRequestDTO;
import com.example.hit_networking_base.domain.dto.request.CVUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.CvResponseDTO;
import com.example.hit_networking_base.domain.dto.response.MyCVResponseDTO;
import com.example.hit_networking_base.domain.entity.CV;
import com.example.hit_networking_base.domain.entity.JobPost;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.CVMapper;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.repository.CvRepository;
import com.example.hit_networking_base.repository.JobPostRepository;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.CvService;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.util.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {
    private final Cloudinary cloudinary;
    private final CvRepository cvRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JobPostRepository jobPostRepository;
    private final CVMapper cvMapper;
    private final CloudinaryConfig cloudinaryConfig;

    @Override
    public CvResponseDTO createCv(CVCreateRequestDTO cvCreateRequestDTO) {
        User user = userService.checkToken();
        JobPost post = jobPostRepository.findByPostIdAndDeletedAtIsNull(cvCreateRequestDTO.getPostId())
                .orElseThrow(() -> new RuntimeException(ErrorMessage.Job.ERR_NOT_FOUND_JOB_ID));
        CV cv = new CV();
        cv.setApplyDate(LocalDateTime.now());
        cv.setLinkCV(convertPDFToUrl(cvCreateRequestDTO.getFile(), user.getUsername()));
        cv.setUser(user);
        cv.setJobPost(post);
        cvRepository.save(cv);

        return CvResponseDTO.builder()
                .id(cv.getId())
                .linkCV(cv.getLinkCV())
                .applyDate(cv.getApplyDate())
                .userPostResponseDTO(userMapper.toUserPostResponseDTO(user))
                .postId(post.getPostId())
                .build();
    }

    @Override
    public Page<CvResponseDTO> getCVsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size , Sort.by("applyDate").descending());
        Page<CV>  cVPage = cvRepository.findByJobPostIdAndDeletedAtIsNull(postId, pageable);
        return cVPage.map(this::convertToDTO);
    }

    @Override
    public Page<MyCVResponseDTO> getMyCVS(int page, int size) {
       Pageable pageable = PageRequest.of(page, size, Sort.by("applyDate").descending());
       User user = userService.checkToken();
       Page<CV> cVPage = cvRepository.findByUserIdAndDeletedAtIsNull(user.getUserId(), pageable);
       List<MyCVResponseDTO> myCVResponseDTOS = cVPage.getContent().stream()
               .map(cv -> {
                   MyCVResponseDTO myCVResponseDTO = cvMapper.toMyCVResponseDTO(cv);
                   myCVResponseDTO.setPostId(cv.getJobPost().getPostId());
                   return myCVResponseDTO;
               })
               .collect(Collectors.toList());
       return new PageImpl<>(myCVResponseDTOS, pageable, cVPage.getTotalElements());
    }

    @Override
    public String deleteCV(Long cvId) {
        User user = userService.checkToken();
        CV cv = findById(cvId);
        if(!user.getRole().equals(Role.BQT) && !user.equals(cv.getUser()))
            throw new BadRequestException(ErrorMessage.CV.ERR_NOT_ENOUGH_RIGHTS);
        cv.setDeletedAt(LocalDateTime.now());
        cvRepository.save(cv);
        return "Deleted CV success";
    }

    @Override
    public MyCVResponseDTO updateCV(Long id, CVUpdateRequestDTO cvUpdateRequestDTO) {
        CV cv = findById(id);
        User user = userService.checkToken();
        if(!cv.getUser().equals(user))
            throw new BadRequestException(ErrorMessage.CV.ERR_NOT_ENOUGH_RIGHTS);
        cv.setLinkCV(convertPDFToUrl(cvUpdateRequestDTO.getFile(), user.getUsername()));
        cvRepository.save(cv);
        MyCVResponseDTO myCVResponseDTO = cvMapper.toMyCVResponseDTO(cv);
        myCVResponseDTO.setPostId(cv.getJobPost().getPostId());
        return myCVResponseDTO;
    }

    @Override
    public void downloadCV(Long postId, HttpServletResponse response) {
        User user = userService.checkToken();
        JobPost jobPost = jobPostRepository.findByPostIdAndDeletedAtIsNull(postId).orElseThrow(()
                -> new RuntimeException(ErrorMessage.Job.ERR_NOT_FOUND_JOB_ID));
        if(!jobPost.getCreator().equals(user))
            throw new BadRequestException(ErrorMessage.User.ERR_NOT_ENOUGH_RIGHTS);
        List<CV> cvs = cvRepository.findByJobPost_PostIdAndDeletedAtIsNull(postId);
        if (cvs.isEmpty()) {
            throw new RuntimeException(ErrorMessage.CV.ERR_NOT_FOUND_CVS);
        }

        try {
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=\"CVs_my_post.zip\"");

            ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

            int index = 1;
            for (CV cv : cvs) {
                String fileUrl = cv.getLinkCV();
                URL url = new URL(fileUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                try (InputStream inputStream = conn.getInputStream()) {
                    String fileName = "cv_" + index + "_" + cv.getUser().getUsername() + ".pdf";

                    zipOut.putNextEntry(new ZipEntry(fileName));
                    IOUtils.copy(inputStream, zipOut);
                    zipOut.closeEntry();
                }

                index++;
            }

            zipOut.finish();
            zipOut.close();

        } catch (IOException e) {
            throw new BadRequestException(ErrorMessage.CV.ERR_WRONG_DOWNLOAD + e);
        }
    }


    private CvResponseDTO convertToDTO(CV cv) {
        return CvResponseDTO.builder()
                .id(cv.getId())
                .linkCV(cv.getLinkCV())
                .applyDate(cv.getApplyDate())
                .userPostResponseDTO(userMapper.toUserPostResponseDTO(cv.getUser()))
                .postId(cv.getJobPost().getPostId())
                .build();
    }

    public String convertPDFToUrl(MultipartFile file, String username) {
        try {
            if (!Objects.equals(file.getContentType(), "application/pdf")) {
                throw new BadRequestException(ErrorMessage.CV.ERR_NOT_FOUND_CV_PDF);
            }

            String filename = username + "_CV";

            Map<String, Object> params = new HashMap<>();
            params.put("resource_type", "raw");
            params.put("public_id", filename);
            params.put("use_filename", true);
            params.put("unique_filename", false);

            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), params);
            return result.get("secure_url").toString();
        } catch (Exception e) {
            throw new BadRequestException(ErrorMessage.CV.ERR_WRONG_LOAD);
        }
    }


    private CV findById(Long id){
        return cvRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new RuntimeException(ErrorMessage.CV.ERR_NOT_FOUND_CV_ID));
    }
}
