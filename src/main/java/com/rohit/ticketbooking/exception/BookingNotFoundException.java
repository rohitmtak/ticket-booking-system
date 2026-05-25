package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends ApiException {

    public BookingNotFoundException(Long bookingId) {
        super(HttpStatus.NOT_FOUND, "Booking not found with id: " + bookingId);
    }
}
