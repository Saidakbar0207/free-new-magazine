package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.PostCreateDTO;
import org.example.free_new_magazine.dto.PostResponseDTO;
import org.example.free_new_magazine.entity.Post;
import org.example.free_new_magazine.entity.PostImage;
import org.example.free_new_magazine.entity.PostTag;
import org.example.free_new_magazine.entity.PostVideo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mappings({
            @Mapping(target = "categoryId", source = "category.id"),
            @Mapping(target = "categoryName", source = "category.name"),
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "authorName", source = "author.username"),
            @Mapping(target = "authorAvatar", source = "author.avatarImage"),
            @Mapping(target = "coverUrl", source = "coverUrl"),

            @Mapping(target = "images", source = "images"),
            @Mapping(target = "videos", source = "videos"),
            @Mapping(target = "tags", source = "postTags")
    })
    PostResponseDTO toResponseDTO(Post post);

    @Mappings({
            @Mapping(target = "images", ignore = true),
            @Mapping(target = "videos", ignore = true),
            @Mapping(target = "tags", ignore = true)
    })
    PostCreateDTO toDTO(Post post);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "category", ignore = true),
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "images", ignore = true),
            @Mapping(target = "videos", ignore = true),
            @Mapping(target = "postTags", ignore = true),
            @Mapping(target = "views", ignore = true),
            @Mapping(target = "featured", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "isDeleted", ignore = true),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "coverType", ignore = true),
            @Mapping(target = "coverUrl", ignore = true)
    })
    Post toEntity(PostCreateDTO dto);


    default List<String> mapImages(List<PostImage> images) {
        if (images == null) return List.of();
        return images.stream()
                .map(PostImage::getImageUrl)
                .filter(s -> s != null && !s.isBlank())
                .toList();
    }

    default List<String> mapVideos(List<PostVideo> videos) {
        if (videos == null) return List.of();
        return videos.stream()
                .map(PostVideo::getVideoUrl)
                .filter(s -> s != null && !s.isBlank())
                .toList();
    }

    default List<String> mapTags(List<PostTag> tags) {
        if (tags == null) return List.of();
        return tags.stream()
                .map(t -> t.getTag() != null ? t.getTag().getName() : null)
                .filter(s -> s != null && !s.isBlank())
                .toList();
    }
}
