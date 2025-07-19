package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentIdAndDeletedAtIsNull(long commentId);

    @Query("SELECT c FROM Comment c WHERE c.targetId = :targetId AND c.targetType = :targetType AND c.deletedAt IS NULL")
    List<Comment> findByTargetIdAndTargetType(long targetId, TargetType targetType);
}
