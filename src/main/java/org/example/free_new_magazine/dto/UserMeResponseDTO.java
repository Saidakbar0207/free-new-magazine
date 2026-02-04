package org.example.free_new_magazine.dto;

import lombok.Data;
import org.apache.catalina.Role;

@Data
public class UserMeResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String bio;
    private String avatarImage;
    private String bannerImage;
    private String color;
}
