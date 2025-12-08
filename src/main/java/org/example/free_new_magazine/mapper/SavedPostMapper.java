package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.SavedPostDTO;
import org.example.free_new_magazine.entity.SavedPost;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SavedPostMapper {
    SavedPostDTO toDTO(SavedPost savedPost);
    SavedPost toEntity(SavedPostDTO savedPostDTO);
}
