package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.CommentDTO;
import org.example.free_new_magazine.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO toDTO(Comment comment);
    Comment toEntity(CommentDTO commentDTO);
}
