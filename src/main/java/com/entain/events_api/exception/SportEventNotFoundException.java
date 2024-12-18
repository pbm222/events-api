package com.entain.events_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Sport event not found")
public class SportEventNotFoundException extends RuntimeException{

    public SportEventNotFoundException(Long id) {
        super("Sport event with not found with id: " + id);
    }
}
