package ru.practicum.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Данные нового пользователя")
public class UserDto {

    @Schema(description = "Идентификатор")
    @NotNull
    private Long id;

    @Schema(description = "Почтовый адрес", example = "ivan.petrov@practicummail.ru")
    @NotBlank
    private String email;

    @Schema(description = "Имя", example = "Иван Петров")
    @NotBlank
    private String name;

    @Schema(description = "Рейтинг")
    private Float rating;

}
