package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.RegisterDTO;
import org.example.free_new_magazine.dto.UserDTO;
import org.example.free_new_magazine.dto.UserMeResponseDTO;
import org.example.free_new_magazine.dto.UserResponseDTO;
import org.example.free_new_magazine.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role",expression = "java(user.getRole().name())")
    UserResponseDTO toResponse(User user);

    @Mapping(target = "role",expression = "java(user.getRole().name())")
    UserMeResponseDTO toMeResponse(User user);

    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User user);

    User toEntity(RegisterDTO registerDTO);
}

