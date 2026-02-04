package org.example.free_new_magazine.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.*;
import org.example.free_new_magazine.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody RegisterDTO dto) {
        UserResponseDTO saved = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO login) {
        return ResponseEntity.ok(authService.login(login));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Successfully logged out"));
    }
}

