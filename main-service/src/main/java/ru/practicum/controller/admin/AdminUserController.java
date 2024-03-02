package ru.practicum.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.UserCreateDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.mapper.UserMapper;
import ru.practicum.service.UserService;
import ru.practicum.utils.PaginationCustom;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "${default.url.path.admin}/users")
@RequiredArgsConstructor
@Tag(name = "Admin: Пользователи", description = "API для работы с пользователями")
public class AdminUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление нового пользователя")
    public UserDto create(@Valid @RequestBody UserCreateDto userCreateDto) {
        log.info("Create user {}", userCreateDto);
        UserDto userDto = userMapper.toUserDto(userService.create(userMapper.toUser(userCreateDto)));
        log.info("Create user success");
        return userDto;
    }

    @GetMapping
    @Operation(summary = "Получение информации о пользователях")
    public List<UserDto> getByIds(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get users ids {}, from {}, size {}", ids, from, size);
        List<UserDto> result = userMapper.toUserDto(userService.getByIds(ids, PaginationCustom.getPageable(from, size)));
        log.info("Get success user - size {}", result.size());
        return result;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление пользователя")
    public void deleteById(@PathVariable Long userId) {
        log.info("Delete user id {}", userId);
        userService.deleteById(userId);
        log.info("Delete user success");
    }
}
