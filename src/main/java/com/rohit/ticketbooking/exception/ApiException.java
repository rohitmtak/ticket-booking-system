package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

/**
 * Base type for all domain exceptions in the ticket-booking system.
 * Carries the HTTP status the global handler should translate it to,
 * so each subclass declares its own status once instead of the handler
 * needing a giant if/else chain.
 */
public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;

    protected ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    protected ApiException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
