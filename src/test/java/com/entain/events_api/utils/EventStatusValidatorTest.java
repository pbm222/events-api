package com.entain.events_api.utils;

import com.entain.events_api.exception.InvalidEventStatusChangeException;
import com.entain.events_api.events.sport_events.model.EventStatus;
import com.entain.events_api.events.sport_events.model.SportEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventStatusValidatorTest {

    @Test
    void shouldThrowExceptionWhenChangingFinishedEventStatus() {
        SportEvent sportEvent = new SportEvent();
        sportEvent.setStatus(EventStatus.FINISHED);

        assertThrows(
                InvalidEventStatusChangeException.class,
                () -> EventStatusValidator.validateStatusChange(sportEvent, EventStatus.ACTIVE),
                "Cannot change status of finished event"
        );
    }

    @Test
    void shouldThrowExceptionWhenActivatingPastEvent() {
        SportEvent sportEvent = new SportEvent();
        sportEvent.setStatus(EventStatus.INACTIVE);
        sportEvent.setStartTime(LocalDateTime.now().minusHours(1));

        assertThrows(
                InvalidEventStatusChangeException.class,
                () -> EventStatusValidator.validateStatusChange(sportEvent, EventStatus.ACTIVE),
                "Cannot activate an event if its start time is in the past"
        );
    }

    @Test
    void shouldThrowExceptionWhenChangingInactiveToFinished() {
        SportEvent sportEvent = new SportEvent();
        sportEvent.setStatus(EventStatus.INACTIVE);

        assertThrows(
                InvalidEventStatusChangeException.class,
                () -> EventStatusValidator.validateStatusChange(sportEvent, EventStatus.FINISHED),
                "Cannot change inactive event to finished"
        );
    }

    @Test
    void shouldNotThrowExceptionForValidStatusChange() {
        SportEvent sportEvent = new SportEvent();
        sportEvent.setStatus(EventStatus.INACTIVE);
        sportEvent.setStartTime(LocalDateTime.now().plusHours(1));

        assertDoesNotThrow(() -> EventStatusValidator.validateStatusChange(sportEvent, EventStatus.ACTIVE));
    }

    @Test
    void shouldAllowChangingActiveToFinished() {
        SportEvent sportEvent = new SportEvent();
        sportEvent.setStatus(EventStatus.ACTIVE);

        assertDoesNotThrow(() -> EventStatusValidator.validateStatusChange(sportEvent, EventStatus.FINISHED));
    }
}
