package com.rohit.ticketbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(nullable = false)
    private LocalDateTime bookedAt;

    @Column(nullable = false, unique = true)
    private String idempotencyKey;

}
