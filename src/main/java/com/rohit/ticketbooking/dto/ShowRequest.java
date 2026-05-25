package com.rohit.ticketbooking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowRequest {
    private String movieName;
    private String venue;
    private Integer totalSeats;
    private LocalDateTime showTime;
}
