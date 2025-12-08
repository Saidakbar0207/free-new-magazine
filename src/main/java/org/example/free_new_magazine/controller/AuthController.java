package org.example.free_new_magazine.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.LoginDTO;
import org.example.free_new_magazine.dto.TokenDTO;
import org.example.free_new_magazine.dto.UserDTO;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.mapper.UserMapper;
import org.example.free_new_magazine.security.JwtUtils;
import org.example.free_new_magazine.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService service;
    private final UserMapper mapper;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto) {
        User toSave = mapper.toEntity(dto);
        User saved = service.createUser(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String username = authentication.getName();
            String jwt = jwtUtils.generateToken(username);
            return ResponseEntity.ok(new TokenDTO(jwt));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, " Invalid username or password");
        }
    }
}
