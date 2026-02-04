package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.LoginDTO;
import org.example.free_new_magazine.dto.RegisterDTO;
import org.example.free_new_magazine.dto.TokenDTO;
import org.example.free_new_magazine.dto.UserResponseDTO;
import org.example.free_new_magazine.entity.Role;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ConflictException;
import org.example.free_new_magazine.exception.UnauthorizedException;
import org.example.free_new_magazine.mapper.UserMapper;
import org.example.free_new_magazine.repository.UserRepository;
import org.example.free_new_magazine.security.JwtUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;




    public UserResponseDTO register(RegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
       user.setPassword(passwordEncoder.encode(dto.getPassword()));
       user.setRole(Role.ROLE_USER);

       try{
           User saved =userRepository.save(user);
           return userMapper.toResponse(saved);
       }catch (DataIntegrityViolationException e){
           throw new ConflictException("Username or email already exists");
       }
    }

    public TokenDTO login(LoginDTO dto){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new UnauthorizedException("Invalid username or password");
        }
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Username not found "));
        String token = jwtUtils.generateToken(user);
        return new TokenDTO(token);
    }
}

