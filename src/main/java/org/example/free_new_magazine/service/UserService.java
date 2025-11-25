package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ResourceAlreadyExistsException;
import org.example.free_new_magazine.exception.ResourceNotFoundException;
import org.example.free_new_magazine.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {

        if (user.getEmail() != null && repository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already in use");
        }
        if (user.getUsername() != null && repository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already in use");
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User updateUser(Long id, User user) {
        User existing = getUserById(id);

        if (user.getEmail() != null && !user.getEmail().equals(existing.getEmail())
                && repository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already in use");
        }

        if (user.getUsername() != null && !user.getUsername().equals(existing.getUsername())
                && repository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already in use");
        }

        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
        existing.setUsername(user.getUsername());
        existing.setPhone(user.getPhone());
        existing.setBio(user.getBio());
        existing.setWebsite(user.getWebsite());
        existing.setAvatarImage(user.getAvatarImage());
        existing.setBannerImage(user.getBannerImage());
        existing.setColor(user.getColor());
        existing.setExplanation(user.getExplanation());
        existing.setRole(user.getRole());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repository.save(existing);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
