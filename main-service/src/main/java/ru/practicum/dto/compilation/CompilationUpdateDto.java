package ru.practicum.dto.compilation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class CompilationUpdateDto {

    @Length(min = 1, max = 50)
    @Schema(description = "Заголовок подборки", example = "Летние концерты")
    private String title;

    @Schema(description = "Закреплена ли подборка на главной странице сайта")
    private Boolean pinned;

    @Schema(description = "Список идентификаторов событий входящих в подборку")
    private List<Long> events;
}
