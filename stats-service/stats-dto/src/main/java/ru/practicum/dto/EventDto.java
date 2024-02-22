package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Data
@Schema(name = "Событие", description = "ДТО фиксации статистики")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Past
    private LocalDateTime timestamp;
}
