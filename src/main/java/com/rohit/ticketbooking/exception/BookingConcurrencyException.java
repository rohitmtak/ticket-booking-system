package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class BookingConcurrencyException extends ApiException {

    public BookingConcurrencyException(String message, Throwable cause) {
        super(HttpStatus.CONFLICT, message, cause);
    }
}
