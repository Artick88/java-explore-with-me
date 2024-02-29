package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.user.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class EventShortDto {
    @Schema(description = "Краткое описание события", example = "Сплав на байдарках похож на полет.")
    @NotBlank
    private String annotation;

    @NotNull
    @Schema(description = "Категория")
    private CategoryDto category;

    @Schema(description = "Количество одобренных заявок на участие в данном событии", example = "5")
    private Integer confirmedRequests;

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
    @Schema(description = "Нужно ли оплачивать участие в событии", example = "true")
    private boolean paid;

    @NotBlank
    @Schema(description = "Заголовок события", example = "Сплав на байдарках")
    private String title;

    @Schema(description = "Количество просмотрев события", example = "999")
    private Integer views;
}
