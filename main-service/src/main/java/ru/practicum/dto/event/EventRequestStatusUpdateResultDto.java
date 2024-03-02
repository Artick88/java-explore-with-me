package ru.practicum.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.request.RequestDto;

import java.util.List;

@Data
@Builder
@Schema(description = "Результат подтверждения/отклонения заявок на участие в событии")
public class EventRequestStatusUpdateResultDto {

    @Schema(description = "Подтвержднные заявки")
    List<RequestDto> confirmedRequests;

    @Schema(description = "Отклоненные заявки")
    List<RequestDto> rejectedRequests;
}
