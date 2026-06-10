package com.rohit.ticketbooking.controller;

import com.rohit.ticketbooking.dto.UserRequest;
import com.rohit.ticketbooking.dto.UserResponse;
import com.rohit.ticketbooking.entity.User;
import com.rohit.ticketbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
    }
}
