package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.response.EventPageResponseDTO;
import com.example.hit_networking_base.domain.dto.response.EventResponseDTO;
import com.example.hit_networking_base.domain.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventRequest eventRequest);
    EventResponseDTO toEventResponseDTO(Event event);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "reactionResponseDTOS", ignore = true)
    @Mapping(target = "commentResponseDTOS", ignore = true)
    EventPageResponseDTO toEventPageResponse(Event event);
}
