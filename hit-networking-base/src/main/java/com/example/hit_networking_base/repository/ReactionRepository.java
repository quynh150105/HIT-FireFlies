package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query("SELECT r FROM Reaction r WHERE r.targetId = :targetId AND r.targetType = :targetType AND r.deletedAt IS NULL")
    List<Reaction> findByTargetIdAndTargetType(Long targetId, TargetType targetType);
}
