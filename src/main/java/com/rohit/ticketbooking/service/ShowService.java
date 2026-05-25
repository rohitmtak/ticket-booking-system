package com.rohit.ticketbooking.service;

import com.rohit.ticketbooking.dto.ShowRequest;
import com.rohit.ticketbooking.entity.*;
import com.rohit.ticketbooking.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public Show createShow(ShowRequest request) {
        // 1. validate request
        if (request.getMovieName() == null || request.getMovieName().isBlank()) {
            throw new RuntimeException("Movie name is required");
        }
        if (request.getTotalSeats() == null || request.getTotalSeats() <= 0) {
            throw new RuntimeException("Total seats must be greater than 0");
        }
        if (request.getShowTime() == null) {
            throw new RuntimeException("Show time is required");
        }

        // 2. persist the show
        Show show = new Show();
        show.setMovieName(request.getMovieName());
        show.setVenue(request.getVenue());
        show.setTotalSeats(request.getTotalSeats());
        show.setShowTime(request.getShowTime());
        Show savedShow = showRepository.save(show);

        // 3. auto-provision seats for the show (S1..Sn, all AVAILABLE)
        List<Seat> seats = new ArrayList<>(request.getTotalSeats());
        for (int i = 1; i <= request.getTotalSeats(); i++) {
            Seat seat = new Seat();
            seat.setShow(savedShow);
            seat.setSeatNumber("S" + i);
            seat.setStatus(SeatStatus.AVAILABLE);
            seats.add(seat);
        }
        seatRepository.saveAll(seats);

        return savedShow;
    }

    public List<Show> listShows() {
        return showRepository.findAll();
    }

    public Show getShow(Long showId) {
        return showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
    }

    public List<Seat> listSeatsForShow(Long showId) {
        // ensure the show exists so we return a clear 404-style error rather than an empty list
        showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        return seatRepository.findByShowId(showId);
    }
}
