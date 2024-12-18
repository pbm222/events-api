package com.entain.events_api.sport_events.dto;

import com.entain.events_api.sport_events.model.EventStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SportEventStatusChangeDto {

    @NotNull(message = "Status cannot be null")
    private EventStatus newStatus;
}
