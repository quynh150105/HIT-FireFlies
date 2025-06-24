package com.example.hit_networking_base.domain.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    private TargetType targetType;

    @Column(name = "target_id")
    private Integer targetId;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public enum TargetType {
        JOBPOST, EVENT, COMMENT
    }
}