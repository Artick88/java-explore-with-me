package ru.practicum.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventUpdateAdminDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.UpdateEventMapper;
import ru.practicum.service.EventService;
import ru.practicum.utils.PaginationCustom;
import ru.practicum.utils.enums.StateEvent;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Admin: События", description = "API для работы с событиями")
@Slf4j
@RestController
@RequestMapping("/${default.url.path.admin}/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UpdateEventMapper updateEventMapper;

    @PatchMapping("/{eventId}")
    @Operation(summary = "Редактирование данных события и его статуса (отклонение/публикация).")
    public EventFullDto update(@PathVariable Long eventId,
                               @Valid @RequestBody EventUpdateAdminDto eventUpdateAdminDto) {
        log.info("Admin update event {}, data {}", eventId, eventUpdateAdminDto);
        EventFullDto result = eventMapper.toEventFullDto(eventService.updateIsAdmin(eventId,
                updateEventMapper.toEvent(eventUpdateAdminDto)));
        log.info("Admin update event success");
        return result;
    }

    @GetMapping
    @Operation(summary = "Поиск событий")
    public List<EventFullDto> getAll(@RequestParam(required = false) List<Long> users,
                                     @RequestParam(required = false) List<StateEvent> states,
                                     @RequestParam(required = false) List<Long> categories,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                     @RequestParam(defaultValue = "0") Integer from,
                                     @RequestParam(defaultValue = "10") Integer size) {
        log.info("Admin get event by filter: users {}, states {}, categories {},  rangeStart {}, rangeEnd {}, from {}, size {}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        List<EventFullDto> result = eventMapper.toEventStatFullDto(eventService.searchAdmin(users, states, categories,
                rangeStart, rangeEnd, PaginationCustom.getPageable(from, size)));
        log.info("Admin get event by filter success {}", result.size());
        return result;
    }

}
