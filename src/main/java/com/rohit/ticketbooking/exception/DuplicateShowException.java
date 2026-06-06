package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class DuplicateShowException extends ApiException {
    public DuplicateShowException(String movieName, LocalDateTime showTime, String venue) {
        super(HttpStatus.CONFLICT, "Show already exists for movie: " + movieName + " at " + showTime + " in venue: " + venue);
    }
}