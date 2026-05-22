package com.rohit.ticketbooking.service;

import com.rohit.ticketbooking.entity.*;
import com.rohit.ticketbooking.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SeatService seatService;

    @Transactional
    public Booking initiateBooking(Long userId, Long seatId, String idempotencyKey) {

        // 1. idempotency check in db
        return bookingRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {
                    try {
                        // 2. validate user
                        User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                        // 3. delegate locking to SeatService
                        Seat lockedSeat = seatService.lockSeat(seatId);

                        // 4. create booking record
                        Booking booking = new Booking();
                        booking.setUser(user);
                        booking.setSeat(lockedSeat);
                        booking.setStatus(BookingStatus.PENDING);
                        booking.setBookedAt(LocalDateTime.now());
                        booking.setIdempotencyKey(idempotencyKey);

                        // save And Flush forces the DB to check constraints immediately inside the try block
                        return bookingRepository.saveAndFlush(booking);

                    } catch (DataIntegrityViolationException e) {
                        return bookingRepository.findByIdempotencyKey(idempotencyKey)
                                .orElseThrow(() -> new RuntimeException("Concurrency error: Booking lost in race condition", e));
                    }
                });
    }

    @Transactional
    public void  completeBooking(Long bookingId) {
        // 1. fetch and validate booking
        Booking booking = getAndValidateBooking(bookingId, BookingStatus.PENDING, "Only PENDING bookings can be completed");

        // 2. update booking status to BOOKED
        booking.setStatus(BookingStatus.BOOKED);
        bookingRepository.save(booking);

        // 3. update seat to BOOKED
        seatService.confirmSeat(booking.getSeat().getId());
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        // 1. fetch and validate booking is in PENDING state
        Booking booking = getAndValidateBooking(bookingId, BookingStatus.PENDING, "Only PENDING bookings can be cancelled");

        // 2. update booking status to CANCELLED
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        // 3. release seat back to AVAILABLE
        seatService.releaseSeat(booking.getSeat().getId());
    }

    @Transactional
    public void failBooking(Long bookingId) {
        // 1. fetch and validate booking
        Booking booking = getAndValidateBooking(bookingId, BookingStatus.PENDING, "Only PENDING bookings can be failed");

        // 2. update booking status to FAILED
        booking.setStatus(BookingStatus.FAILED);
        bookingRepository.save(booking);

        // 3. release seat back to AVAILABLE
        seatService.releaseSeat(booking.getSeat().getId());
    }

    private Booking getAndValidateBooking(Long bookingId, BookingStatus expectedStatus, String errorMessage) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != expectedStatus) {
            throw new RuntimeException(errorMessage);
        }

        return booking;
    }
}
