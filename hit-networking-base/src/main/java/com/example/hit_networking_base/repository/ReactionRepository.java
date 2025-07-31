package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query("SELECT r FROM Reaction r WHERE r.targetId = :targetId AND r.targetType = :targetType AND r.deletedAt IS NULL")
    List<Reaction> findByTargetIdAndTargetType(Long targetId, TargetType targetType);

    @Query("SELECT r FROM Reaction r WHERE r.targetId = :targetId AND r.targetType = :targetType AND r.user.id = :userId AND r.deletedAt IS NULL")
    Optional<Reaction> findByUserIdAndTarget( Long targetId,TargetType targetType, Long userId);

    @Query("SELECT r.emotionType, COUNT(r) FROM Reaction r WHERE r.targetId = :targetId AND r.targetType = :targetType AND r.deletedAt IS NULL GROUP BY r.emotionType")
    List<Object[]> countReactionsByType(Long targetId, TargetType targetType);

}
