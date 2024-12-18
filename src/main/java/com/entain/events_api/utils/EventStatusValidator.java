package com.entain.events_api.utils;

import com.entain.events_api.exception.InvalidEventStatusChangeException;
import com.entain.events_api.sport_events.model.EventStatus;
import com.entain.events_api.sport_events.model.SportEvent;

import java.time.LocalDateTime;

import static com.entain.events_api.sport_events.model.EventStatus.*;

public class EventStatusValidator {

    public static void validateStatusChange(SportEvent sportEvent, EventStatus newStatus) {

        EventStatus currentStatus = sportEvent.getStatus();
        if (FINISHED.equals(currentStatus)) {
            throw new InvalidEventStatusChangeException("Cannot change status of finished event");
        }

        if (hasEventStarted(sportEvent) && ACTIVE.equals(newStatus)) {
            throw new InvalidEventStatusChangeException("Cannot activate an event if its start time is in the past");
        }

        if (INACTIVE.equals(currentStatus) && FINISHED.equals(newStatus)) {
            throw new InvalidEventStatusChangeException("Cannot change inactive event to finished");
        }
    }

    private static boolean hasEventStarted(SportEvent sportEvent) {
        return sportEvent.getStartTime() != null
                && sportEvent.getStartTime().isBefore(LocalDateTime.now());
    }
}
