package ru.practicum.controller.publ;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.service.CompilationService;
import ru.practicum.utils.PaginationCustom;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Public: Подборки событий", description = "Публичный API для работы с подборками событий")
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    @Operation(summary = "Получение подборки событий по его id")
    public CompilationDto getById(@PathVariable Long compId) {
        log.info("Get compilation by id {}", compId);
        CompilationDto result = compilationService.getById(compId);
        log.info("Get compilation success");
        return result;
    }

    @GetMapping
    @Operation(summary = "Получение подборок событий")
    public List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        log.info("Get compilation filter: pinned {}, from {}, size {}", pinned, from, size);
        List<CompilationDto> result = compilationService.getAll(pinned, PaginationCustom.getPageable(from, size));
        log.info("Get compilation filter success");
        return result;
    }


}
