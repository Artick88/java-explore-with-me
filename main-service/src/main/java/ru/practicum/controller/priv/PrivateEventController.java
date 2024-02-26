package ru.practicum.controller.priv;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.*;
import ru.practicum.dto.request.RequestDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.mapper.UpdateEventMapper;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;
import ru.practicum.utils.PaginationCustom;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Tag(name = "Private: События", description = "Закрытый API для работы с событиями")
public class PrivateEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UpdateEventMapper updateEventMapper;
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление нового события")
    public EventFullDto create(@PathVariable Long userId,
                               @Valid @RequestBody EventCreateDto eventCreateDto) {
        log.info("User {} create event {}", userId, eventCreateDto);
        EventFullDto result = eventMapper.toEventFullDto(eventService.create(userId, eventMapper.toEvent(eventCreateDto)));
        log.info("User create event success");
        return result;
    }

    @GetMapping
    @Operation(summary = "Получение событий, добавленных текущим пользователем")
    public List<EventShortDto> getUserEvents(@PathVariable Long userId,
                                             @RequestParam(defaultValue = "0") int from,
                                             @RequestParam(defaultValue = "10") int size) {
        log.info("User {} get events with parameters: from {}, size {}", userId, from, size);
        List<EventShortDto> result = eventMapper.toEventShorDto(eventService.getUserEvents(userId, PaginationCustom.getPageable(from, size)));
        log.info("User get event size {} success", result.size());
        return result;
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "Получение полной информации о событии добавленном текущим пользователем")
    public EventFullDto getUserEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("User {} get event by id {}", userId, eventId);
        EventFullDto result = eventMapper.toEventFullDto(eventService.getUserEventById(userId, eventId));
        log.info("User get event success");
        return result;
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "Изменение события добавленного текущим пользователем")
    public EventFullDto update(@PathVariable Long userId,
                               @PathVariable Long eventId,
                               @Valid @RequestBody EventUpdateUserDto eventUpdateUserDto) {
        log.info("User {} update event {} data {}", userId, eventId, eventUpdateUserDto);
        EventFullDto result = eventMapper.toEventFullDto(eventService.update(userId, eventId,
                updateEventMapper.toEvent(eventUpdateUserDto)));
        log.info("User update event success");
        return result;
    }

    @GetMapping("/{eventId}/requests")
    @Operation(summary = "Получение информации о запросах на участие в событии текущего пользователя")
    public List<RequestDto> getRequestByEventId(@PathVariable Long userId,
                                                @PathVariable Long eventId) {
        log.info("User {} get request by event {}", userId, eventId);
        List<RequestDto> result = requestMapper.toRequestDto(requestService.getAllByEventId(userId, eventId));
        log.info("User get request event success");
        return result;
    }

    @PatchMapping("/{eventId}/requests")
    @Operation(summary = "Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя")
    public EventRequestStatusUpdateResultDto confirmRequest(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @Valid @RequestBody EventRequestStatusUpdateRequestDto request) {
        log.info("User {} update status request for event {}, data {}", userId, eventId, request);
        EventRequestStatusUpdateResultDto result = requestMapper.toUpdateRequestResultDtoCustom(
                requestService.updateStatus(userId, eventId, requestMapper.toUpdateRequest(request)));
        log.info("User get request event success");
        return result;
    }
}
