package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i WHERE i.targetType = :targetType AND i.targetId = :targetId AND i.deletedAt IS NULL")
    List<Image> findByTargetTypeAndTargetId(TargetType targetType, long targetId);
}
