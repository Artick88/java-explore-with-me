package ru.practicum.exception;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Hidden
public class ErrorResponse {
    private final String error;
}
