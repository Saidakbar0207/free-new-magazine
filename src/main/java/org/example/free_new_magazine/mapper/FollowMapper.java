package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.FollowDTO;
import org.example.free_new_magazine.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FollowMapper {


    @Mapping(target = "followerId", source ="follower.id" )
    @Mapping(target = "followingId", source ="following.id" )
    @Mapping(target = "followerUsername", source ="follower.username" )
    @Mapping(target = "followingUsername", source ="following.username" )
    FollowDTO toDTO(Follow follow);


    Follow toEntity(FollowDTO followDTO);
}
