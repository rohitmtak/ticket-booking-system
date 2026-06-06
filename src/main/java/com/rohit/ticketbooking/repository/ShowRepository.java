package com.rohit.ticketbooking.repository;

import com.rohit.ticketbooking.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    boolean existsByMovieNameAndShowTimeAndVenue(
        String movieName,
        LocalDateTime showTime,
        String venue
    ); 
}