package com.entain.events_api.events.sport_events;

import com.entain.events_api.events.sport_events.dto.SportEventDto;
import com.entain.events_api.events.sport_events.mapper.SportEventMapper;
import com.entain.events_api.events.sport_events.model.EventStatus;
import com.entain.events_api.events.sport_events.model.SportEvent;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SportEventMapperTest {

    private final SportEventMapper mapper = Mappers.getMapper(SportEventMapper.class);

    @Test
    void shouldMapDtoToSportEvent() {
        SportEventDto dto = new SportEventDto(1L,"Event Name", "Soccer", "ACTIVE", LocalDateTime.now());

        SportEvent sportEvent = mapper.dtoToSportEvent(dto);

        assertNotNull(sportEvent);
        assertEquals(dto.getName(), sportEvent.getName());
        assertEquals(dto.getSport(), sportEvent.getSport());
        assertEquals(EventStatus.valueOf(dto.getStatus()), sportEvent.getStatus());
        assertEquals(dto.getStartTime(), sportEvent.getStartTime());
    }

    @Test
    void shouldMapSportEventToDto() {
        SportEvent sportEvent = new SportEvent();
        sportEvent.setName("Event Name");
        sportEvent.setSport("Soccer");
        sportEvent.setStatus(EventStatus.ACTIVE);
        sportEvent.setStartTime(LocalDateTime.now());

        SportEventDto dto = mapper.sportEventToDto(sportEvent);

        assertNotNull(dto);
        assertEquals(sportEvent.getName(), dto.getName());
        assertEquals(sportEvent.getSport(), dto.getSport());
        assertEquals(sportEvent.getStatus().toString(), dto.getStatus());
        assertEquals(sportEvent.getStartTime(), dto.getStartTime());
    }

    @Test
    void shouldMapListOfSportEventsToDtos() {
        SportEvent event1 = new SportEvent();
        event1.setName("Event1");
        event1.setSport("Soccer");
        event1.setStatus(EventStatus.ACTIVE);
        event1.setStartTime(LocalDateTime.now());

        SportEvent event2 = new SportEvent();
        event2.setName("Event2");
        event2.setSport("Basketball");
        event2.setStatus(EventStatus.INACTIVE);
        event2.setStartTime(LocalDateTime.now().plusDays(1));

        List<SportEventDto> dtos = mapper.sportEventsToDtos(List.of(event1, event2));

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(event1.getName(), dtos.get(0).getName());
        assertEquals(event2.getName(), dtos.get(1).getName());
    }

    @Test
    void shouldMapListOfDtosToSportEvents() {
        SportEventDto dto1 = new SportEventDto(1L, "Event1", "Soccer", "ACTIVE", LocalDateTime.now());
        SportEventDto dto2 = new SportEventDto(2L, "Event2", "Basketball", "INACTIVE", LocalDateTime.now().plusDays(1));

        List<SportEvent> events = mapper.dtosToSportEvents(List.of(dto1, dto2));

        assertNotNull(events);
        assertEquals(2, events.size());
        assertEquals(dto1.getName(), events.get(0).getName());
        assertEquals(dto2.getName(), events.get(1).getName());
    }
}
