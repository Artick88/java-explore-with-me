package ru.practicum.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Широта и долгота места проведения события")
public class LocationDto {

    @Schema(description = "Широта", example = "55.754167")
    private Float lat;

    @Schema(description = "Долгота", example = "37.62")
    private Float lon;
}
