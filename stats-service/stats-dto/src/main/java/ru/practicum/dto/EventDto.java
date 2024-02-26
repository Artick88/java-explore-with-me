package ru.practicum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(name = "Событие", description = "ДТО фиксации статистики")
@Builder
public class EventDto {

    @Schema(description = "Идентификатор сервиса для которого записывается информация", example = "ewm-main-service")
    @NotBlank
    private String app;

    @Schema(description = "URI для которого был осуществлен запрос", example = "/events/1")
    @NotBlank
    private String uri;

    @Schema(description = "IP-адрес пользователя, осуществившего запрос", example = "192.163.0.1")
    @NotBlank
    private String ip;

    @Schema(description = "Дата и время, когда был совершен запрос к эндпоинту (в формате \"yyyy-MM-dd HH:mm:ss\")",
            example = "2022-09-06 11:00:23")
    @NotNull
    private String timestamp;
}
