package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.constant.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeProjection {
    private long postId;
    private LocalDateTime createdAt;
    private TargetType targetType;

    public HomeProjection(Object[] homeDTO){
        this.postId = ((Number) homeDTO[0]).longValue();
        this.createdAt = ((Timestamp) homeDTO[1]).toLocalDateTime();
        this.targetType = TargetType.valueOf(String.valueOf(homeDTO[2]));
    }
}
