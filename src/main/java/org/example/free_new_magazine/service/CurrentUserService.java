package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.NotFoundException;
import org.example.free_new_magazine.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser(){
        String username  = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException("User not found"));

    }
}
