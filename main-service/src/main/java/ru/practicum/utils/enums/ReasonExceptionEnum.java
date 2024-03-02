package ru.practicum.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonExceptionEnum {

    CONFLICT("Conflict action"),
    NOT_FOUND("Not found object"),
    NO_ACCESS("No access to object"),
    SQL("Database problem"),
    BAD_PARAMETER("Bad request. Bad parameters");

    private final String reason;
}
