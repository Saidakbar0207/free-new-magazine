package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.LikeDTO;
import org.example.free_new_magazine.entity.Like;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    LikeDTO toDTO(Like like);
    Like toEntity(LikeDTO likeDTO);
}
