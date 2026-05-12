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
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String movieName;

    private String venue;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private LocalDateTime showTime;
}
