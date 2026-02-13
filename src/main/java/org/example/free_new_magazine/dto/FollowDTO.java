package org.example.free_new_magazine.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowDTO {

    private Long id;

    @NotNull(message = "Following id is required")
    private Long followingId;

    @NotNull(message = "Follower id is required")
    private Long followerId;

    private String followerUsername;

    private String followingUsername;

    private LocalDateTime followDate;

}
