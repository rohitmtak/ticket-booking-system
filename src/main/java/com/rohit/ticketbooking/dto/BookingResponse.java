package com.rohit.ticketbooking.dto;

import com.rohit.ticketbooking.entity.Booking;
import com.rohit.ticketbooking.entity.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponse {

    private Long bookingId;
    private Long userId;
    private Long seatId;
    private String seatNumber;
    private BookingStatus status;
    private LocalDateTime bookedAt;
    private LocalDateTime paymentDeadline;

    public static BookingResponse from(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setUserId(booking.getUser().getId());
        response.setSeatId(booking.getSeat().getId());
        response.setSeatNumber(booking.getSeat().getSeatNumber());
        response.setStatus(booking.getStatus());
        response.setBookedAt(booking.getBookedAt());
        response.setPaymentDeadline(booking.getSeat().getLockExpiresAt());
        return response;
    }
}
