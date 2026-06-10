package com.rohit.ticketbooking.service;

import com.rohit.ticketbooking.dto.UserRequest;
import com.rohit.ticketbooking.entity.User;
import com.rohit.ticketbooking.exception.DuplicateUserException;
import com.rohit.ticketbooking.exception.InvalidUserRequestException;
import com.rohit.ticketbooking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(UserRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidUserRequestException("Name is required");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new InvalidUserRequestException("Email is required");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException(request.getEmail());
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(request.getEmail().trim());

        try {
            return userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUserException(request.getEmail());
        }
    }
}
