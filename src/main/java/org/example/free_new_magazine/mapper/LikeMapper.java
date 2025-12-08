package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.LikeDTO;
import org.example.free_new_magazine.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LikeMapper {
    LikeDTO toDTO(Like like);
    Like toEntity(LikeDTO likeDTO);
}
