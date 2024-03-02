package ru.practicum.exception;

import lombok.Getter;

@Getter
public class NoSuchEnumException extends RuntimeException {

    private final String reason;

    public NoSuchEnumException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}