package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.UserDTO;
import org.example.free_new_magazine.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
