package com.rohit.ticketbooking.controller;

import com.rohit.ticketbooking.dto.BookingRequest;
import com.rohit.ticketbooking.dto.BookingResponse;
import com.rohit.ticketbooking.entity.Booking;
import com.rohit.ticketbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/initiate")
    public ResponseEntity<BookingResponse> initiateBooking(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @RequestBody BookingRequest request) {
        Booking booking = bookingService.initiateBooking(
                request.getUserId(),
                request.getSeatId(),
                idempotencyKey
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(BookingResponse.from(booking));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<String> completeBooking(@PathVariable("id") Long bookingId) {
        bookingService.completeBooking(bookingId);
        return ResponseEntity.ok("Booking successfully completed and seat confirmed");
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled and seat released");
    }
}
