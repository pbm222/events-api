package com.entain.events_api.events.sport_events.controller;

import com.entain.events_api.events.sport_events.dto.SportEventStatusChangeDto;
import com.entain.events_api.events.sport_events.model.EventStatus;
import com.entain.events_api.events.sport_events.service.SportEventsService;
import com.entain.events_api.events.sport_events.dto.SportEventDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sport-events")
public class SportEventsController {

    private final SportEventsService sportEventsService;

    @Autowired
    public SportEventsController(SportEventsService sportEventsService) {
        this.sportEventsService = sportEventsService;
    }

    @GetMapping
    // * The annotation could be placed on the controller to reduce code repetition,
    // however in real life each method usually has different role/privilege required
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SportEventDto>> getSportEventsByStatusAndSport(
            @RequestParam(value = "status", required = false) EventStatus status,
            @RequestParam(value = "sport", required = false) String sport) {

        return ResponseEntity.ok(
                sportEventsService.getSportEventsByStatusAndSport(status, sport));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SportEventDto> getSportEventById(@PathVariable Long id) {
        return ResponseEntity.ok(sportEventsService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SportEventDto> createSportEvent(@Valid @RequestBody SportEventDto sportEventDto) {
        return ResponseEntity.ok(sportEventsService.saveSportEvent(sportEventDto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateSportEventStatus(@PathVariable Long id,
                                                         @Valid @RequestBody SportEventStatusChangeDto statusChangeDto) {
        sportEventsService.updateSportEventStatus(id, statusChangeDto.getNewStatus());
        return ResponseEntity.ok("Status updated for event with id: " + id);
    }
}
