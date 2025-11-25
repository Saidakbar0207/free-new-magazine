package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.TagDTO;
import org.example.free_new_magazine.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDTO(Tag tag);
    Tag toEntity(TagDTO tagDTO);
}
