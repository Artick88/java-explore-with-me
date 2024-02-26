package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class EventCreateDto {

    @Schema(description = "Краткое описание события", example = "Сплав на байдарках похож на полет.")
    @NotBlank
    @Length(max = 2000, min = 20)
    private String annotation;

    @NotNull
    @Schema(description = "id категории к которой относится событие", example = "2")
    private Integer category;

    @NotBlank
    @Length(max = 7000, min = 20)
    @Schema(example = "Сплав на байдарках похож на полет. На спокойной воде — это парение. " +
            "На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, " +
            "феерические эмоции, яркие впечатления.", description = "Полное описание события")
    private String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата и время на которые намечено событие.", example = "2024-12-31 15:10:05")
    private LocalDateTime eventDate;

    @NotNull
    @Schema(description = "Широта и долгота места проведения события")
    private LocationDto location;

    @Schema(defaultValue = "false", description = "Нужно ли оплачивать участие в событии", example = "true")
    private Boolean paid;

    @Schema(description = "Ограничение на количество участников. Значение 0 - означает отсутствие ограничения",
            example = "10", defaultValue = "0")
    @Min(0)
    private Integer participantLimit;

    @Schema(defaultValue = "true", example = "false", description = "Нужна ли пре-модерация заявок на участие. " +
            "Если true, то все заявки будут ожидать подтверждения инициатором события. " +
            "Если false - то будут подтверждаться автоматически.")
    private Boolean requestModeration;

    @NotBlank
    @Length(max = 120, min = 3)
    @Schema(description = "Заголовок события", example = "Сплав на байдарках")
    private String title;
}
