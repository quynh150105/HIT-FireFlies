package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
//    @Query("SELECT i FROM Image i WHERE i.targetType = :targetType AND i.targetId = :id")
    List<Image> findByTargetTypeAndTargetId(TargetType targetType, long targetId);
}
