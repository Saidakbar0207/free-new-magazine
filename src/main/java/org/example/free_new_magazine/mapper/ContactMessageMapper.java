package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.PostDTO;
import org.example.free_new_magazine.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMessageMapper {

    ContactMessageMapper INSTANCE = Mappers.getMapper(ContactMessageMapper.class);

    PostDTO toDTO(Post post);

    Post toEntity(PostDTO postDTO);
}
