package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.response.MyCVResponseDTO;
import com.example.hit_networking_base.domain.entity.CV;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CVMapper {
    MyCVResponseDTO toMyCVResponseDTO(CV cv);
}
