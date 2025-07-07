package com.example.hit_networking_base.domain.entity;



import com.example.hit_networking_base.constant.EmotionType;
import com.example.hit_networking_base.constant.TargetType;
import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "reaction")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion_type")
    private EmotionType emotionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    private TargetType targetType;

    @Column(name = "target_id")
    private long targetId;

    @Column(name = "reacted_at")
    private LocalDateTime reactedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}