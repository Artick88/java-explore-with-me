package ru.practicum.exception;

import lombok.Getter;

@Getter
public class NotValidRequestException extends RuntimeException {

    private final String reason;

    public NotValidRequestException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}
