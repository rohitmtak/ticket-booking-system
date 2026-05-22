package com.rohit.ticketbooking.service;

import com.rohit.ticketbooking.entity.*;
import com.rohit.ticketbooking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public Seat lockSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        LocalDateTime now = LocalDateTime.now();

        // 1. check if it's permanently gone
        if (seat.getStatus() == SeatStatus.BOOKED) {
            throw new RuntimeException("Seat already sold");
        }

        // 2. if it is PENDING but the expiry time has passed, explicitly reset it to AVAILABLE!
        if (seat.getStatus() == SeatStatus.PENDING &&
                seat.getLockExpiresAt() != null &&
                seat.getLockExpiresAt().isBefore(now)) {

                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setLockExpiresAt(null);
        }

        // 3. check if it's currently locked (Active lock window)
        if (seat.getStatus() == SeatStatus.PENDING) {
            throw new RuntimeException("Seat is currently held by another user");
        }

        // 4. Proceed with locking the AVAILABLE seat
        seat.setStatus(SeatStatus.PENDING);
        seat.setLockExpiresAt(now.plusMinutes(10));

        // This save triggers the @Version check!
        return seatRepository.save(seat);
    }

    public void confirmSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setStatus(SeatStatus.BOOKED);
        seat.setLockExpiresAt(null);
        seatRepository.save(seat);
    }

    public void releaseSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setLockExpiresAt(null);
        seatRepository.save(seat);
    }
}

