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

    // POST
    @PostMapping("/initiate")
    public ResponseEntity<?> initiateBooking(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.initiateBooking(
                    request.getUserId(),
                    request.getSeatId(),
                    idempotencyKey
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(BookingResponse.from(booking));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<String> completeBooking(@PathVariable("id") Long bookingId) {
        try {
            bookingService.completeBooking(bookingId);
            return ResponseEntity.ok("Booking successfully completed and seat confirmed");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking cancelled and seat released");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
