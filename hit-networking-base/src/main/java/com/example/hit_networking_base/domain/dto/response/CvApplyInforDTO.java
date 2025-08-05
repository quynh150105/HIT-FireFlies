package com.example.hit_networking_base.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CvApplyInforDTO {
    private String fullName;
    private String linkCV;
}
