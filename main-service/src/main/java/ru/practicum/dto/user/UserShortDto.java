package ru.practicum.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Пользователь (краткая информация)")
public class UserShortDto {

    @Schema(description = "Идентификатор")
    @NotNull
    private Long id;

    @Schema(description = "Имя", example = "Иван Петров")
    @NotBlank
    private String name;
}
