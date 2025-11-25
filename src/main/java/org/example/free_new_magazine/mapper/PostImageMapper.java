package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.PostImageDTO;
import org.example.free_new_magazine.entity.PostImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostImageMapper {
    PostImageDTO toDTO(PostImage postImage);
    PostImage toEntity(PostImageDTO postImageDTO);
}
