package com.entain.events_api.exception;

public class InvalidEventStatusChangeException extends RuntimeException {

    public InvalidEventStatusChangeException(String message) {
        super(message);
    }
}
