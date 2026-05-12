package com.rohit.ticketbooking.repository;

import com.rohit.ticketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Spring will automatically generate:
    // SELECT * FROM bookings WHERE idempotency_key = ?
    Optional<Booking> findByIdempotencyKey(String idempotencyKey);
}