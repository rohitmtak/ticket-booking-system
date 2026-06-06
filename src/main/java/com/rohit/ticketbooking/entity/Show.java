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
@Table(name = "shows", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_shows_movie_time_venue",
                columnNames = {"movie_name", "show_time", "venue"}
        )
})
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private String venue;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private LocalDateTime showTime;
}
