package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class ShowNotFoundException extends ApiException {

    public ShowNotFoundException(Long showId) {
        super(HttpStatus.NOT_FOUND, "Show not found with id: " + showId);
    }
}
