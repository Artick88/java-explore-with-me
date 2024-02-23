package ru.practicum.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "StatsController", description = "API для работы со статистикой посещений")
public class StatController {

    private final StatService statService;
    private final StatsMapper statsMapper;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Информации о том, что к эндпоинту был запрос", description = "Сохранение информации о том, " +
            "что на uri конкретного сервиса был отправлен запрос пользователем. Название сервиса, uri и ip " +
            "пользователя указаны в теле запроса.")
    public void save(@Valid @RequestBody EventDto eventDto) {
        log.info("Save event {}", eventDto);
        statService.save(statsMapper.toEvent(eventDto));
        log.info("Success save event");
    }

    @GetMapping("/stats")
    @Operation(summary = "Получение статистики по посещениям")
    public List<StatsDto> get(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
                              @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
                              @RequestParam(value = "uris", required = false) List<String> uris,
                              @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        log.info("Get stat with parameters: start {}, end {}, unique {}, uris {}", startDate, endDate, unique, uris);
        List<StatsDto> statsDtoList = statsMapper.toStatsDto(statService.get(startDate, endDate, uris, unique));
        log.info("Success get stats {}", statsDtoList);
        return statsDtoList;
    }
}
