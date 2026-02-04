package org.example.free_new_magazine.security;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.repository.UserRepository;
import org.springframework.data.repository.Repository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or username: " + username));
        return new CustomUserDetails(user);
    }
}
