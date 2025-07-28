package com.example.hit_networking_base.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyRequest {
    private Long postId;
    private String linkCV;
    private Long userId;
}
