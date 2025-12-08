package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.UserDTO;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.mapper.UserMapper;
import org.example.free_new_magazine.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        User user = service.getUserByEmail(email);
        return ResponseEntity.ok(mapper.toDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        UserDTO updated = service.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
