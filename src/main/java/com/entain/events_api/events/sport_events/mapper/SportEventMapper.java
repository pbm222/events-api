package com.entain.events_api.events.sport_events.mapper;

import com.entain.events_api.events.sport_events.dto.SportEventDto;
import com.entain.events_api.events.sport_events.model.SportEvent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SportEventMapper {

    SportEvent dtoToSportEvent(SportEventDto eventDto);
    SportEventDto sportEventToDto(SportEvent event);

    List<SportEventDto> sportEventsToDtos(List<SportEvent> events);
    List<SportEvent> dtosToSportEvents(List<SportEventDto> eventDtos);
}
