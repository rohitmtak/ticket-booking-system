package com.rohit.ticketbooking.exception;

import org.springframework.http.HttpStatus;

public class SeatAlreadyBookedException extends ApiException {

    public SeatAlreadyBookedException(Long seatId) {
        super(HttpStatus.CONFLICT, "Seat " + seatId + " has already been booked");
    }
}
