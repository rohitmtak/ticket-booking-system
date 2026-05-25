package com.rohit.ticketbooking.controller;

import com.rohit.ticketbooking.dto.SeatResponse;
import com.rohit.ticketbooking.dto.ShowRequest;
import com.rohit.ticketbooking.dto.ShowResponse;
import com.rohit.ticketbooking.entity.Show;
import com.rohit.ticketbooking.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    // POST
    @PostMapping
    public ResponseEntity<?> createShow(@RequestBody ShowRequest request) {
        try {
            Show show = showService.createShow(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(ShowResponse.from(show));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // GET all
    @GetMapping
    public ResponseEntity<List<ShowResponse>> listShows() {
        List<ShowResponse> shows = showService.listShows().stream()
                .map(ShowResponse::from)
                .toList();
        return ResponseEntity.ok(shows);
    }

    // GET one
    @GetMapping("/{id}")
    public ResponseEntity<?> getShow(@PathVariable("id") Long showId) {
        try {
            Show show = showService.getShow(showId);
            return ResponseEntity.ok(ShowResponse.from(show));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET seats for a show
    @GetMapping("/{id}/seats")
    public ResponseEntity<?> listSeats(@PathVariable("id") Long showId) {
        try {
            List<SeatResponse> seats = showService.listSeatsForShow(showId).stream()
                    .map(SeatResponse::from)
                    .toList();
            return ResponseEntity.ok(seats);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
