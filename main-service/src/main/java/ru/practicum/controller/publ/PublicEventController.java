package ru.practicum.controller.publ;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.service.EventService;
import ru.practicum.utils.PaginationCustom;
import ru.practicum.utils.enums.SortEvent;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Public: События", description = "Публичный API для работы с событиями")
@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Получение подробной информации об опубликованном событии по его идентификатору")
    public EventFullDto getById(@PathVariable Long id,
                                HttpServletRequest httpServletRequest) {
        log.info("Get event by id {}", id);
        EventFullDto result = eventMapper.toEventFullDto(eventService.getById(id, httpServletRequest));
        log.info("Get event success");
        return result;
    }

    @GetMapping
    @Operation(summary = "Получение событий с возможностью фильтрации")
    public List<EventShortDto> search(HttpServletRequest httpServletRequest,
                                      @RequestParam(required = false) @Length(min = 1, max = 7000) String text,
                                      @RequestParam(required = false) List<Long> categories,
                                      @RequestParam(required = false) Boolean paid,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                      @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                      @RequestParam(required = false) SortEvent sort,
                                      @RequestParam(defaultValue = "0") Integer from,
                                      @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get event by filter: text {}, categories {}, paid {}, rangeStart {}, rangeEnd {}, onlyAvailable {}," +
                "sort {}, from {}, size {}", text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        List<EventShortDto> result = eventMapper.toEventStatShortDto(eventService.search(httpServletRequest, text, categories,
                paid, rangeStart, rangeEnd, onlyAvailable, sort, PaginationCustom.getPageable(from, size)));
        log.info("Get event success size {}", result.size());
        return result;
    }
}
