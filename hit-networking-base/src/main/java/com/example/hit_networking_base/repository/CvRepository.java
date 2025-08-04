package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.CV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CvRepository extends JpaRepository<CV,Long> {

    @Query("SELECT c FROM CV c WHERE c.jobPost.postId = :postId AND c.deletedAt IS NULL")
    Page<CV> findByJobPostIdAndDeletedAtIsNull(Long postId, Pageable pageable);

    @Query("SELECT c FROM CV c WHERE c.user.userId = :userId AND c.deletedAt IS NULL")
    Page<CV> findByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable);

    Optional<CV> findByIdAndDeletedAtIsNull(Long id);

    List<CV> findByJobPost_PostIdAndDeletedAtIsNull(Long postId);

    @Query("SELECT COUNT(DISTINCT c.jobPost.postId) FROM CV c WHERE c.user.userId = :userId AND c.deletedAt IS NULL")
    Long countJobPostAppliedByUser(Long userId);


}
