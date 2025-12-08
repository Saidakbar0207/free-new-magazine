package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.FollowDTO;
import org.example.free_new_magazine.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FollowMapper {
    FollowDTO toDTO(Follow follow);
    Follow toEntity(FollowDTO followDTO);
}
