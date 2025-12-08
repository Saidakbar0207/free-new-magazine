package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.UserDTO;
import org.example.free_new_magazine.entity.Role;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ResourceAlreadyExistsException;
import org.example.free_new_magazine.exception.ResourceNotFoundException;
import org.example.free_new_magazine.mapper.UserMapper;
import org.example.free_new_magazine.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final CurrentUserService currentUserService;
    private final AuditLogService auditLogService;
    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        User user = currentUserService.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("Only ADMIN can create category");
        }
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    public UserDTO getUserById(Long id) {
        User user = getUserEntityById(id);

        User currentUser = currentUserService.getCurrentUser();
        if(!user.getId().equals(currentUser.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("You are not allowed to view this user");
        }

        return mapper.toDTO(user);
    }

    private User getUserEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

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

    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existing = getUserEntityById(id);
        User user = currentUserService.getCurrentUser();

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
        User updatedUser = repository.save(existing);
        return mapper.toDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
