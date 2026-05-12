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

        // 2. check if it's currently locked
        if (seat.getStatus() == SeatStatus.PENDING &&
                seat.getLockExpiresAt() != null &&
                seat.getLockExpiresAt().isAfter(now)) {
            throw new RuntimeException("Seat is currently held by another user");
        }

        seat.setStatus(SeatStatus.PENDING);
        seat.setLockExpiresAt(now.plusMinutes(10));

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

