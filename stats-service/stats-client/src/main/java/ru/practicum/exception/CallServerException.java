package ru.practicum.exception;

public class CallServerException extends RuntimeException {
    public CallServerException(String message) {
        super(message);
    }
}
