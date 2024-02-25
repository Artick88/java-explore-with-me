package ru.practicum.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "Данные нового пользователя")
public class UserCreateDto {

    @Schema(description = "Почтовый адрес", example = "ivan.petrov@practicummail.ru")
    @NotBlank
    private String email;

    @Schema(description = "Имя", example = "Иван Петров")
    @NotBlank
    private String name;
}
