package org.example.free_new_magazine.dto;

import lombok.Data;

@Data
public class UserMeUpdateRequestDTO {
    private String email;

    private String oldPassword;
    private String newPassword;

    private String firstName;
    private String lastName;
    private String bio;
    private String avatarImage;
    private String bannerImage;
    private String color;
}
