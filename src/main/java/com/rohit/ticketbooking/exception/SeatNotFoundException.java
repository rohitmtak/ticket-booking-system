package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class SeatNotFoundException extends ApiException {

    public SeatNotFoundException(Long seatId) {
        super(HttpStatus.NOT_FOUND, "Seat not found with id: " + seatId);
    }
}
