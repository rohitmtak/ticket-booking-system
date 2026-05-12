package com.rohit.ticketbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @Column(nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private LocalDateTime lockExpiresAt;

    @Version
    private Long version;
}
