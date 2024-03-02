package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.utils.enums.StateUserActionEvent;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@Schema(description = "Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.")
public class EventUpdateUserDto {

    @Schema(description = "Краткое описание события", example = "Сплав на байдарках похож на полет.")
    @Length(max = 2000, min = 20)
    private String annotation;

    @Schema(description = "id категории к которой относится событие", example = "2")
    private Long category;

    @Length(max = 7000, min = 20)
    @Schema(example = "Сплав на байдарках похож на полет. На спокойной воде — это парение. " +
            "На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, " +
            "феерические эмоции, яркие впечатления.", description = "Полное описание события")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата и время на которые намечено событие.", example = "2024-12-31 15:10:05")
    private LocalDateTime eventDate;

    @Schema(description = "Широта и долгота места проведения события")
    private LocationDto location;

    @Schema(description = "Нужно ли оплачивать участие в событии", example = "true")
    private Boolean paid;

    @Schema(description = "Ограничение на количество участников. Значение 0 - означает отсутствие ограничения",
            example = "10")
    @Min(0)
    private Integer participantLimit;

    @Schema(example = "false", description = "Нужна ли пре-модерация заявок на участие. " +
            "Если true, то все заявки будут ожидать подтверждения инициатором события. " +
            "Если false - то будут подтверждаться автоматически.")
    private Boolean requestModeration;

    @Length(max = 120, min = 3)
    @Schema(description = "Заголовок события", example = "Сплав на байдарках")
    private String title;

    @Schema(description = "Состояние события")
    private StateUserActionEvent stateAction;
}
