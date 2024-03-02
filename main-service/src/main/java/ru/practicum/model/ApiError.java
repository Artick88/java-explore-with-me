package ru.practicum.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(description = "Сведения об ошибке")
@Builder
@Getter
@Setter
public class ApiError {

    @Schema(description = "Сообщение об ошибке")
    private String message;

    @Schema(description = "Общее описание причины ошибки")
    private String reason;

    @Schema(description = "Код статуса HTTP-ответа")
    private HttpStatus status;

    @Schema(description = "Дата и время когда произошла ошибка")
    private LocalDateTime timestamp;

    @Schema(description = "Список стектрейсов или описания ошибок")
    private String errors;
}
