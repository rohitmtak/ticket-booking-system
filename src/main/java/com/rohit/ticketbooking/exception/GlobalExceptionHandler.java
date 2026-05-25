package com.rohit.ticketbooking.exception;

import com.rohit.ticketbooking.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centralised translation of exceptions to HTTP responses.
 * Controllers stay free of try/catch — anything thrown from a service or
 * controller lands here and gets mapped to a consistent {@link ErrorResponse}.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Catches every {@link ApiException} subclass in one place.
     * The HTTP status to use lives on the exception itself, so adding a new
     * domain exception requires no changes here.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        HttpStatus status = ex.getStatus();
        log.warn("API exception at {}: {} -> {}", request.getRequestURI(), ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.status(status).body(
                ErrorResponse.of(status.value(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI())
        );
    }

    /**
     * Fallback for anything we didn't anticipate.
     * Logs the full stack trace but returns an opaque 500 to the client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception at {}", request.getRequestURI(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(
                ErrorResponse.of(status.value(), status.getReasonPhrase(), "An unexpected error occurred", request.getRequestURI())
        );
    }
}
