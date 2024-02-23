package ru.practicum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Статистика", description = "ДТО обшей информации статистики")
public class StatsDto {

    @Schema(description = "Название сервиса", example = "ewm-main-service")
    private String app;

    @Schema(description = "URI сервиса", example = "/events/1")
    private String uri;

    @Schema(description = "Количество просмотров", example = "6")
    private Long hits;
}
