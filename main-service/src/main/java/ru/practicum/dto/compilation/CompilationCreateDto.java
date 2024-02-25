package ru.practicum.dto.compilation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Schema(description = "Подборка событий")
public class CompilationCreateDto {

    @NotBlank
    @Length(min = 1, max = 50)
    @Schema(description = "Заголовок подборки", example = "Летние концерты")
    private String title;

    @Schema(description = "Закреплена ли подборка на главной странице сайта", defaultValue = "false", example = "false")
    private Boolean pinned;

    @Schema(description = "Список идентификаторов событий входящих в подборку")
    private List<Long> events;

}
