package org.example.free_new_magazine.dto;

import lombok.Data;
import org.apache.catalina.Role;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String role;


}
