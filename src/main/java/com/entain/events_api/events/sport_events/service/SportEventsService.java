package com.entain.events_api.events.sport_events.service;

import com.entain.events_api.events.sport_events.dto.SportEventDto;
import com.entain.events_api.exception.SportEventNotFoundException;
import com.entain.events_api.events.sport_events.mapper.SportEventMapper;
import com.entain.events_api.events.sport_events.model.EventStatus;
import com.entain.events_api.events.sport_events.model.SportEvent;
import com.entain.events_api.events.sport_events.repository.SportEventsRepository;
import com.entain.events_api.utils.EventStatusValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportEventsService {

    private final SportEventsRepository repository;

    private final SportEventMapper mapper;

    public List<SportEventDto> getSportEventsByStatusAndSport(EventStatus status, String sport) {
        List<SportEvent> sportEvents = (status == null && sport == null)
                ? repository.findAll()
                : repository.findByStatusOrSport(status, sport);
        return mapper.sportEventsToDtos(sportEvents);
    }

    public SportEventDto saveSportEvent(SportEventDto sportEventDto) {
        SportEvent sportEvent = repository.save(mapper.dtoToSportEvent(sportEventDto));
        return mapper.sportEventToDto(sportEvent);
    }

    public SportEventDto getById(Long id) {
        SportEvent sportEvent = repository.findById(id)
                .orElseThrow(() -> new SportEventNotFoundException(id));
        return mapper.sportEventToDto(sportEvent);
    }

    public void updateSportEventStatus(Long id, EventStatus status) {
        SportEvent sportEvent = repository.findById(id)
                .orElseThrow(() -> new SportEventNotFoundException(id));

        EventStatusValidator.validateStatusChange(sportEvent, status);
        sportEvent.setStatus(status);
        repository.save(sportEvent);
    }
}
