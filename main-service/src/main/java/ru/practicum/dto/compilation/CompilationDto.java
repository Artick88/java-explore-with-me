package ru.practicum.dto.compilation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.event.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Schema(description = "Подборка событий")
@Builder
public class CompilationDto {

    @NotNull
    @Schema(description = "Идентификатор")
    private Long id;

    @NotBlank
    @Schema(description = "Наименование подборки")
    private String title;

    @NotNull
    @Schema(description = "Закреплена ли подборка на главной странице сайта")
    private Boolean pinned;

    @Schema(description = "Список событий входящих в подборку")
    private Set<EventShortDto> events;
}
