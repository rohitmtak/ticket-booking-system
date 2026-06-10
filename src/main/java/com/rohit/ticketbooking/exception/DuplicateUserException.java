package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class DuplicateUserException extends ApiException {

    public DuplicateUserException(String email) {
        super(HttpStatus.CONFLICT, "User already exists with email: " + email);
    }
}
