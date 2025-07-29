package com.example.hit_networking_base.domain.entity;



import com.example.hit_networking_base.constant.EmotionType;
import com.example.hit_networking_base.constant.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "reaction",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "target_id", "target_type"}))
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}