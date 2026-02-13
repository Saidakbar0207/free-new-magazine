package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.PostTagDTO;
import org.example.free_new_magazine.entity.PostTag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostTagMapper {
    List<PostTagDTO> toDTO(List<PostTag> postTags);
    PostTagDTO toDTO(PostTag postTag);
    PostTag toEntity(PostTagDTO postTagDTO);
}
