package org.example.free_new_magazine.dto;

import lombok.Data;
import org.example.free_new_magazine.entity.CoverType;
import org.example.free_new_magazine.entity.PostStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {
    private Long id;

    private String title;
    private String content;

    private String categoryId;
    private String categoryName;

    private String coverUrl;
    private CoverType coverType;

    private Long authorId;
    private String authorName;
    private String authorAvatar;

    private Boolean featured;
    private PostStatus status;

    private Long views;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private List<String> images;
    private List<String> videos;
    private List<String> tags;

}
