package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.CategoryDTO;
import org.example.free_new_magazine.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
