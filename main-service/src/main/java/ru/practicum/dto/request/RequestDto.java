package ru.practicum.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.practicum.utils.enums.StatusRequest;

import java.time.LocalDateTime;

@Data
@Schema(description = "Заявка на участие в событии")
public class RequestDto {

    @Schema(description = "Дата и время создания заявки", example = "2022-09-06T21:10:05.432")
    private LocalDateTime created;

    @Schema(description = "Идентификатор события", example = "1")
    private Long event;

    @Schema(description = "Идентификатор заявки", example = "1")
    private Long id;

    @Schema(description = "Идентификатор пользователя, отправившего заявку", example = "2")
    private Long requester;

    @Schema(description = "Статус заявки", example = "PENDING")
    private StatusRequest status;
}
