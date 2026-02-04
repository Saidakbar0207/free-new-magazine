package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.PostCreateDTO;
import org.example.free_new_magazine.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMessageMapper {

    ContactMessageMapper INSTANCE = Mappers.getMapper(ContactMessageMapper.class);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "videos", ignore = true)
    @Mapping(target = "tags", ignore = true)
    PostCreateDTO toDTO(Post post);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "videos", ignore = true)
    @Mapping(target = "postTags", ignore = true)
    Post toEntity(PostCreateDTO postCreateDTO);
}
