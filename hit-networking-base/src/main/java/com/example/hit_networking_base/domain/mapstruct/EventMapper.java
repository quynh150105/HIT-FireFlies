package com.example.hit_networking_base.domain.mapstruct;

import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
import com.example.hit_networking_base.domain.dto.response.EventPageResponseDTO;
import com.example.hit_networking_base.domain.dto.response.EventResponseDTO;
import com.example.hit_networking_base.domain.entity.Event;
import org.mapstruct.*;

@Mapper (componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventRequest eventRequest);
    EventResponseDTO toEventResponseDTO(Event event);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "reactionResponseDTOS", ignore = true)
    @Mapping(target = "commentResponseDTOS", ignore = true)
    EventPageResponseDTO toEventPageResponse(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromRequest(EventUpdateRequest request, @MappingTarget Event event);
}
//=======
//import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
//import com.example.hit_networking_base.domain.dto.response.EventResponse;
//import com.example.hit_networking_base.domain.entity.Event;
//import org.mapstruct.*;
//
//@Mapper(componentModel = "spring")
//public interface EventMapper {
//    EventResponse toEventResponse(Event event);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateEventFromRequest(EventUpdateRequest request, @MappingTarget Event event);
//}
//>>>>>>> origin/feature/upadate-event
