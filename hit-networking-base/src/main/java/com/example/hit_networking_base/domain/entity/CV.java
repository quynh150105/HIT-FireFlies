package com.example.hit_networking_base.domain.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cv")
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "apply_date")
    private LocalDateTime applyDate;

    @Column(name = "linkCV", length = 225)
    private String linkCV;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private JobPost jobPost;
}