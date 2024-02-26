package ru.practicum.controller.priv;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.RequestDto;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Private: Запросы на участие",
        description = "Закрытый API для работы с запросами текущего пользователя на участие в событиях")
public class PrivateRequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @GetMapping
    @Operation(summary = "Получение информации о заявках текущего пользователя на участие в чужих событиях")
    public List<RequestDto> getAll(@PathVariable Long userId) {
        log.info("Get requests user {}", userId);
        List<RequestDto> result = requestMapper.toRequestDto(requestService.getAllByUserId(userId));
        log.info("Get requests success size {}", result.size());
        return result;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление запроса от текущего пользователя на участие в событии")
    public RequestDto create(@PathVariable Long userId,
                             @RequestParam Long eventId,
                             HttpServletRequest httpServletRequest) {
        log.info("User {} create request in event {}", userId, eventId);
        RequestDto result = requestMapper.toRequestDto(requestService.create(userId, eventId, httpServletRequest));
        log.info("User create request success");
        return result;
    }

    @PatchMapping("/{requestId}/cancel")
    @Operation(summary = "Отмена своего запроса на участие в событии")
    public RequestDto cancelRequest(@PathVariable Long userId,
                                    @PathVariable Long requestId) {
        log.info("User {} cancel request {}", userId, requestId);
        RequestDto result = requestMapper.toRequestDto(requestService.cancelRequest(userId, requestId));
        log.info("User cancel request success");
        return result;
    }
}
