package org.example.free_new_magazine.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTagDTO {

    @NotNull(message = "Post id is required")
    private Long postId;

    @NotNull(message = "Tag id is required")
    private Long tagId;
}
