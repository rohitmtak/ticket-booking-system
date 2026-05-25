package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class InvalidBookingStateException extends ApiException {

    public InvalidBookingStateException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
