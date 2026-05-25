package com.rohit.ticketbooking.dto;

import com.rohit.ticketbooking.entity.Seat;
import com.rohit.ticketbooking.entity.SeatStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeatResponse {

    private Long id;
    private String seatNumber;
    private SeatStatus status;
    private LocalDateTime lockExpiresAt;

    public static SeatResponse from(Seat seat) {
        SeatResponse response = new SeatResponse();
        response.setId(seat.getId());
        response.setSeatNumber(seat.getSeatNumber());
        response.setStatus(seat.getStatus());
        response.setLockExpiresAt(seat.getLockExpiresAt());
        return response;
    }
}
