package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.PostDTO;
import org.example.free_new_magazine.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDTO toDTO(Post post);

    Post toEntity(PostDTO postDTO);
}
