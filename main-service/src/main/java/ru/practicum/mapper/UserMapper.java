package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.user.UserCreateDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.model.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateDto userCreateDto);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);
}
