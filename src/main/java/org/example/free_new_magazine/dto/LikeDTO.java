package org.example.free_new_magazine.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {


    @NotNull(message = "Post id is required")
    private Long postId;

    @NotNull(message = "User id is required")
    private Long userId;

}

