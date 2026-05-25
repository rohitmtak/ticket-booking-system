package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class SeatLockedException extends ApiException {

    public SeatLockedException(Long seatId) {
        super(HttpStatus.CONFLICT, "Seat " + seatId + " is currently held by another user");
    }
}
