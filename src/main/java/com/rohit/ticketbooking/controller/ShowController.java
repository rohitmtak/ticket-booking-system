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

    @PostMapping
    public ResponseEntity<ShowResponse> createShow(@RequestBody ShowRequest request) {
        Show show = showService.createShow(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ShowResponse.from(show));
    }

    @GetMapping
    public ResponseEntity<List<ShowResponse>> listShows() {
        List<ShowResponse> shows = showService.listShows().stream()
                .map(ShowResponse::from)
                .toList();
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> getShow(@PathVariable("id") Long showId) {
        Show show = showService.getShow(showId);
        return ResponseEntity.ok(ShowResponse.from(show));
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<SeatResponse>> listSeats(@PathVariable("id") Long showId) {
        List<SeatResponse> seats = showService.listSeatsForShow(showId).stream()
                .map(SeatResponse::from)
                .toList();
        return ResponseEntity.ok(seats);
    }
}
