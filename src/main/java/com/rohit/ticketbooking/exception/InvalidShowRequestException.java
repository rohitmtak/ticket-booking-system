package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class InvalidShowRequestException extends ApiException {

    public InvalidShowRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
