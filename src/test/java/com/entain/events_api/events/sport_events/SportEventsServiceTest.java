package com.entain.events_api.events.sport_events;

import com.entain.events_api.exception.SportEventNotFoundException;
import com.entain.events_api.events.sport_events.dto.SportEventDto;
import com.entain.events_api.events.sport_events.mapper.SportEventMapper;
import com.entain.events_api.events.sport_events.model.EventStatus;
import com.entain.events_api.events.sport_events.model.SportEvent;
import com.entain.events_api.events.sport_events.repository.SportEventsRepository;
import com.entain.events_api.events.sport_events.service.SportEventsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SportEventsServiceTest {

    @Mock
    private SportEventsRepository repository;

    @Mock
    private SportEventMapper mapper;

    @InjectMocks
    private SportEventsService sportEventsService;

    @Test
    void shouldReturnSportEventsByStatusAndSport() {
        SportEventDto sportEventDto = new SportEventDto(1L, "Test Event", "Football", "ACTIVE", null);
        SportEvent sportEvent = new SportEvent(1L, "Test Event", "Football", EventStatus.ACTIVE, null);

        List<SportEvent> sportEvents = singletonList(sportEvent);
        List<SportEventDto> sportEventDtos = singletonList(sportEventDto);

        when(repository.findByStatusOrSport(EventStatus.ACTIVE, "Football")).thenReturn(sportEvents);
        when(mapper.sportEventsToDtos(sportEvents)).thenReturn(sportEventDtos);

        List<SportEventDto> result = sportEventsService.getSportEventsByStatusAndSport(EventStatus.ACTIVE, "Football");
        
        assertEquals(1, result.size());
        assertEquals("Test Event", result.get(0).getName());
        verify(repository, times(1)).findByStatusOrSport(EventStatus.ACTIVE, "Football");
    }

    @Test
    void shouldReturnAllSportEventsWhenNoStatusAndSportAreProvided() {
        SportEventDto sportEventDto = new SportEventDto(1L, "Test Event", "Football", "ACTIVE", null);
        SportEvent sportEvent = new SportEvent(1L, "Test Event", "Football", EventStatus.ACTIVE, null);
        List<SportEvent> sportEvents = singletonList(sportEvent);
        List<SportEventDto> sportEventDtos = singletonList(sportEventDto);

        when(repository.findAll()).thenReturn(sportEvents);
        when(mapper.sportEventsToDtos(sportEvents)).thenReturn(sportEventDtos);
        
        List<SportEventDto> result = sportEventsService.getSportEventsByStatusAndSport(null, null);
        
        assertEquals(1, result.size());
        assertEquals("Test Event", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldSaveSportEvent() {
        SportEventDto sportEventDto = new SportEventDto(1L, "Test Event", "Football", "ACTIVE", null);
        SportEvent sportEvent = new SportEvent(1L, "Test Event", "Football", EventStatus.ACTIVE, null);

        when(mapper.dtoToSportEvent(sportEventDto)).thenReturn(sportEvent);
        when(repository.save(sportEvent)).thenReturn(sportEvent);
        when(mapper.sportEventToDto(sportEvent)).thenReturn(sportEventDto);

        SportEventDto result = sportEventsService.saveSportEvent(sportEventDto);

        assertEquals("Test Event", result.getName());
        verify(repository, times(1)).save(sportEvent);
    }

    @Test
    void shouldReturnSportEventById() {
        SportEventDto sportEventDto = new SportEventDto(1L, "Test Event", "Football", "ACTIVE", null);
        SportEvent sportEvent = new SportEvent(1L, "Test Event", "Football", EventStatus.ACTIVE, null);

        when(repository.findById(1L)).thenReturn(Optional.of(sportEvent));
        when(mapper.sportEventToDto(sportEvent)).thenReturn(sportEventDto);

        SportEventDto result = sportEventsService.getById(1L);
        
        assertEquals("Test Event", result.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateSportEventStatus() {
        SportEvent sportEvent = new SportEvent(1L, "Test Event", "Football", EventStatus.ACTIVE, null);

        when(repository.findById(1L)).thenReturn(Optional.of(sportEvent));

        sportEventsService.updateSportEventStatus(1L, EventStatus.FINISHED);

        verify(repository, times(1)).save(sportEvent);
        assertEquals(EventStatus.FINISHED, sportEvent.getStatus());
    }

    @Test
    void shouldThrowSportEventNotFoundExceptionWhenUpdatingStatusAndSportEventNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SportEventNotFoundException.class, () -> sportEventsService.updateSportEventStatus(1L, EventStatus.FINISHED));
    }
}
