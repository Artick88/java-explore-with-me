package ru.practicum.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "Данные нового пользователя")
public class UserCreateDto {

    @Schema(description = "Почтовый адрес", example = "ivan.petrov@practicummail.ru")
    @Length(min = 6, max = 254)
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Имя", example = "Иван Петров")
    @Length(min = 2, max = 250)
    @NotBlank
    private String name;
}
