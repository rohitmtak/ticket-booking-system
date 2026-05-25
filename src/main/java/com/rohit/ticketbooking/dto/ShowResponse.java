package com.rohit.ticketbooking.dto;

import com.rohit.ticketbooking.entity.Show;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowResponse {

    private Long id;
    private String movieName;
    private String venue;
    private Integer totalSeats;
    private LocalDateTime showTime;

    public static ShowResponse from(Show show) {
        ShowResponse response = new ShowResponse();
        response.setId(show.getId());
        response.setMovieName(show.getMovieName());
        response.setVenue(show.getVenue());
        response.setTotalSeats(show.getTotalSeats());
        response.setShowTime(show.getShowTime());
        return response;
    }
}
