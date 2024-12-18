package com.entain.events_api.sport_events.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SportEventDto {
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;
    private String sport;
    private String status;
    private LocalDateTime startTime;
}
