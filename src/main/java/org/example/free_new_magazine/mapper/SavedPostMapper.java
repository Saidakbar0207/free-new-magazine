package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.SavedPostDTO;
import org.example.free_new_magazine.entity.SavedPost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SavedPostMapper {
    SavedPostDTO toDTO(SavedPost savedPost);
    SavedPost toEntity(SavedPostDTO savedPostDTO);
}
