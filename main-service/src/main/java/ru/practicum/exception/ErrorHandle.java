package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.model.ApiError;
import ru.practicum.utils.enums.ReasonExceptionEnum;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ErrorHandle {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(ConflictException exception) {
        log.info(exception.getMessage());
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .errors(Arrays.toString(exception.getStackTrace()))
                .reason(exception.getReason())
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException exception) {
        log.info(exception.getMessage());
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .errors(Arrays.toString(exception.getStackTrace()))
                .reason(exception.getReason())
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handlePSQLException(DataIntegrityViolationException exception) {
        log.info(exception.getMessage());
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .errors(Arrays.toString(exception.getStackTrace()))
                .reason(ReasonExceptionEnum.SQL.getReason())
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.info(exception.getMessage());
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .errors(Arrays.toString(exception.getStackTrace()))
                .reason(ReasonExceptionEnum.BAD_PARAMETER.getReason())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(MethodArgumentNotValidException exception) {
        log.info(exception.getMessage());
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .errors(Arrays.toString(exception.getStackTrace()))
                .reason(ReasonExceptionEnum.BAD_PARAMETER.getReason())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNotValidaRequestException(NotValidRequestException exception) {
        log.info(exception.getMessage());
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .errors(Arrays.toString(exception.getStackTrace()))
                .reason(exception.getReason())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
