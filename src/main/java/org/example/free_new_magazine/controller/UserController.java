package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.UserDTO;
import org.example.free_new_magazine.dto.UserMeResponseDTO;
import org.example.free_new_magazine.dto.UserResponseDTO;
import org.example.free_new_magazine.dto.UserMeUpdateRequestDTO;
import org.example.free_new_magazine.service.StorageService;
import org.example.free_new_magazine.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final StorageService storageService;


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/username/{username}")
        public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserMeUpdateRequestDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeResponseDTO> me() {
        return ResponseEntity.ok(userService.getMe());
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<UserMeResponseDTO> updateMe(
                @RequestParam(required = false) String email,
                @RequestParam(required = false) String firstName,
                @RequestParam(required = false) String lastName,
                @RequestParam(required = false) String bio,
                @RequestParam(required = false) String color,
                @RequestParam(required = false) String oldPassword,
                @RequestParam(required = false) String newPassword,

                @RequestPart(required = false) MultipartFile avatar,
                @RequestPart(required = false) MultipartFile banner
        ) {
            return ResponseEntity.ok(
                    userService.updateMe(email,firstName,lastName,bio,color,
                            oldPassword,newPassword,avatar,banner)
                    );
            }


}
