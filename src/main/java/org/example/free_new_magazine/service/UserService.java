package org.example.free_new_magazine.service;

import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.UserMeResponseDTO;
import org.example.free_new_magazine.dto.UserMeUpdateRequestDTO;
import org.example.free_new_magazine.dto.UserResponseDTO;
import org.example.free_new_magazine.entity.Role;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.NotFoundException;
import org.example.free_new_magazine.mapper.UserMapper;
import org.example.free_new_magazine.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final CurrentUserService currentUserService;
    private final AuditLogService auditLogService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final StorageService storageService;

    public List<UserResponseDTO> getAllUsers() {
        User user = currentUserService.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("Only ADMIN can create category");
        }
        return repository.findAll()
                .stream().map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = getUserEntityById(id);
        User currentUser = currentUserService.getCurrentUser();
        if(!user.getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("You are not allowed to view this user");
        }

        return userMapper.toResponse(user);
    }

    public UserMeResponseDTO getMe(){
        User user = currentUserService.getCurrentUser();
        return userMapper.toMeResponse(user);
    }

    public UserMeResponseDTO updateMe(
            String email, String firstName, String lastName, String bio, String color,
            String oldPassword, String newPassword,
            MultipartFile avatar, MultipartFile banner
    ){
        User user = currentUserService.getCurrentUser();

        if(email != null && !email.isBlank() && !email.equals(user.getEmail())
            && repository.existsByEmail(email)){
            throw new NotFoundException("Email already in use");
        }
        if(email != null && !email.isBlank()) user.setEmail(email);
        if(firstName != null && !firstName.isBlank()) user.setFirstName(firstName);
        if(lastName != null && !lastName.isBlank()) user.setLastName(lastName);
        if(bio != null && !bio.isBlank()) user.setBio(bio);
        if(color != null && !color.isBlank()) user.setColor(color);

        if(avatar != null && !avatar.isEmpty()) {
            user.setAvatarImage(storageService.save(avatar, "users/avatars"));
        }
        if(banner != null && !banner.isEmpty()) {
            user.setBannerImage(storageService.save(banner, "users/banners"));
        }
        if(newPassword !=null && !newPassword.isBlank()){
            if(oldPassword == null || oldPassword.isBlank()){
                throw new BadRequestException("Old password is required");
            }
            if(!passwordEncoder.matches(oldPassword, user.getPassword())){
                throw new BadRequestException("Old password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        User saved = repository.save(user);
        return userMapper.toMeResponse(saved);
    }


    public UserResponseDTO getUserByEmail(String email) {
        User userNotFound = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toResponse(userNotFound);
    }

    @Transactional
    public void deleteUser(Long id) {
        User target = getUserEntityById(id);
        User currentUser = currentUserService.getCurrentUser();
                if(!target.getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ROLE_ADMIN) {
                    throw new AccessDeniedException("You are not allowed to delete this user");
                }

        repository.deleteById(target.getId());
    }

    User getUserEntityById(Long id) {
       return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserResponseDTO updateUser(Long id, UserMeUpdateRequestDTO dto) {
        User existing = getUserEntityById(id);
        User current = currentUserService.getCurrentUser();

        if (!existing.getId().equals(current.getId()) && current.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("You are not allowed to update this user");
        }

        String normalizedEmail = normalizeEmail(dto.getEmail());

        if (normalizedEmail != null && !normalizedEmail.equalsIgnoreCase(existing.getEmail())
                && repository.existsByEmail(normalizedEmail)) {
            throw new NotFoundException("Email already in use");
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(existing.getEmail())
                && repository.existsByUsername(dto.getEmail())) {
            throw new NotFoundException("Username already in use");
        }

        if (normalizedEmail != null) existing.setEmail(normalizedEmail);
        setIfPresent(dto.getFirstName(), existing::setFirstName);
        setIfPresent(dto.getLastName(), existing::setLastName);
        setIfPresent(dto.getBio(), existing::setBio);
        setIfPresent(dto.getColor(), existing::setColor);

        if (dto.getAvatarImage() != null) existing.setAvatarImage(dto.getAvatarImage());
        if (dto.getBannerImage() != null) existing.setBannerImage(dto.getBannerImage());

        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            if (dto.getOldPassword() == null || dto.getOldPassword().isBlank()) {
                throw new BadRequestException("Old password is required");
            }
            if (!passwordEncoder.matches(dto.getOldPassword(), existing.getPassword())) {
                throw new BadRequestException("Old password is incorrect");
            }
            existing.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        User saved = repository.save(existing);
        return userMapper.toResponse(saved);
    }

    private void setIfPresent(String value, java.util.function.Consumer<String> setter) {
        if (value != null && !value.isBlank()) {
            setter.accept(value.trim());
        }
    }

    private String normalizeEmail(String email) {
        if (email == null) return null;
        String e = email.trim();
        return e.isBlank() ? null : e.toLowerCase();
    }

    public UserResponseDTO getUserByUsername(String username) {
        if(username == null || username.isBlank()) return null;
        return repository.findByUsername(username)
                .map(userMapper::toResponse)
                .orElse(null);
    }
}
