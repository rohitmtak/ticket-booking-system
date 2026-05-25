package com.rohit.ticketbooking.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long userId;
    private Long seatId;
}
