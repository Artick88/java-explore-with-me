package ru.practicum.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.practicum.utils.enums.StatusRequest;

import java.util.List;

@Data
@Schema(description = "Изменение статуса запроса на участие в событии текущего пользователя")
public class EventRequestStatusUpdateRequestDto {

    @Schema(description = "Идентификаторы запросов на участие в событии текущего пользователя", example = "List [ 1, 2, 3 ]")
    private List<Long> requestIds;

    @Schema(description = "Новый статус запроса на участие в событии текущего пользователя", example = "CONFIRMED")
    private StatusRequest status;

}
