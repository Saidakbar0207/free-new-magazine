package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.PostTagDTO;
import org.example.free_new_magazine.entity.PostTag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostTagMapper {
    PostTagDTO toDTO(PostTag postTag);
    PostTag toEntity(PostTagDTO postTagDTO);
}
