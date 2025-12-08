package org.example.free_new_magazine.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.free_new_magazine.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Username cannot be blank")
    private String username;


    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}
