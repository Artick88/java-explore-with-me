package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.utils.enums.StateEvent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class EventFullDto {

    @Schema(description = "Краткое описание события", example = "Сплав на байдарках похож на полет.")
    @NotBlank
    private String annotation;

    @NotNull
    @Schema(description = "Категория")
    private CategoryDto category;

    @Schema(description = "Количество одобренных заявок на участие в данном событии", example = "5")
    private Integer confirmedRequests;

    @Schema(description = "Дата и время создания события (в формате \"yyyy-MM-dd HH:mm:ss\")", example = "2022-09-06 11:00:23")
    private LocalDateTime createdOn;

    @Schema(example = "Сплав на байдарках похож на полет. На спокойной воде — это парение. " +
            "На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, " +
            "феерические эмоции, яркие впечатления.", description = "Полное описание события")
    private String description;

    @NotNull
    @Schema(description = "Дата и время на которые намечено событие.", example = "2024-12-31 15:10:05")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @Schema(description = "Идентификатор", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Пользователь (краткая информация)")
    private UserShortDto initiator;

    @NotNull
    @Schema(description = "Широта и долгота места проведения события")
    private LocationDto location;

    @NotNull
    @Schema(description = "Нужно ли оплачивать участие в событии", example = "true")
    private boolean paid;

    @Schema(description = "Ограничение на количество участников. Значение 0 - означает отсутствие ограничения",
            example = "10", defaultValue = "0")
    private Integer participantLimit;

    @Schema(description = "Дата и время публикации события (в формате \"yyyy-MM-dd HH:mm:ss\")", example = "2022-09-06 15:10:05")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    @Schema(defaultValue = "true", example = "false", description = "Нужна ли пре-модерация заявок на участие")
    private boolean requestModeration;

    @Schema(description = "Список состояний жизненного цикла события", example = "PUBLISHED")
    private StateEvent state;

    @NotBlank
    @Schema(description = "Заголовок события", example = "Сплав на байдарках")
    private String title;

    @Schema(description = "Количество просмотрев события", example = "999")
    private Integer views;

    @Schema(description = "Кол-во лайков")
    private Integer countLike;

    @Schema(description = "Кол-во дизлайков")
    private Integer countDislike;

    @Schema(description = "Рейтинг")
    private Float rating;
}
