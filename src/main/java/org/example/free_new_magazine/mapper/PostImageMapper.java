package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.PostImageDTO;
import org.example.free_new_magazine.entity.PostImage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostImageMapper {
    PostImageDTO toDTO(PostImage postImage);
    PostImage toEntity(PostImageDTO postImageDTO);
}
