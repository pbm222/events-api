package com.entain.events_api.sport_events;

import com.entain.events_api.sport_events.controller.SportEventsController;
import com.entain.events_api.sport_events.dto.SportEventDto;
import com.entain.events_api.sport_events.dto.SportEventStatusChangeDto;
import com.entain.events_api.sport_events.model.EventStatus;
import com.entain.events_api.sport_events.service.SportEventsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SportEventsController.class)
class SportEventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SportEventsService sportEventsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getSportEventsByStatusAndSportShouldReturnList() throws Exception {
        SportEventDto eventDto = new SportEventDto(1L, "Event1", "Soccer", "ACTIVE", LocalDateTime.now());
        when(sportEventsService.getSportEventsByStatusAndSport(null, null))
                .thenReturn(List.of(eventDto));

        mockMvc.perform(get("/sport-events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Event1"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getSportEventByIdShouldReturnEvent() throws Exception {
        SportEventDto eventDto = new SportEventDto(1L, "Event1", "Soccer", "ACTIVE", LocalDateTime.now());
        when(sportEventsService.getById(1L)).thenReturn(eventDto);

        mockMvc.perform(get("/sport-events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Event1"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createSportEventShouldReturnCreatedEvent() throws Exception {
        SportEventDto eventDto = new SportEventDto(1L, "Event1", "Soccer", "ACTIVE", LocalDateTime.now());
        when(sportEventsService.saveSportEvent(any(SportEventDto.class))).thenReturn(eventDto);

        mockMvc.perform(post("/sport-events")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Event1"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createSportEventShouldReturnBadRequestWhenNameIsNull() throws Exception {
        SportEventDto invalidDto = new SportEventDto(1L, null, "Soccer", "ACTIVE", LocalDateTime.now());

        mockMvc.perform(post("/sport-events")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSportEventStatusShouldReturnSuccessMessage() throws Exception {
        SportEventStatusChangeDto statusChangeDto = new SportEventStatusChangeDto(EventStatus.ACTIVE);

        mockMvc.perform(patch("/sport-events/1/status")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusChangeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Status updated for event with id: 1"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSportEventStatusShouldReturnBadRequestWhenInvalidEnumValue() throws Exception {
        String invalidStatusJson = "{ \"newStatus\": \"INVALID\" }";

        mockMvc.perform(patch("/sport-events/1/status")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidStatusJson))
                .andExpect(status().isBadRequest());
    }
}
