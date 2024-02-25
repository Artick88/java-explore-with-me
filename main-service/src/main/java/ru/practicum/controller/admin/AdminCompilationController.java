package ru.practicum.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationCreateDto;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.CompilationUpdateDto;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@Tag(name = "Admin: Подборки событий", description = "API для работы с подборками событий")
@RequestMapping("${default.url.path.admin}/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {

    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление новой подборки (подборка может не содержать событий)")
    public CompilationDto create(@Valid @RequestBody CompilationCreateDto compilationCreateDto) {
        log.info("Create compilation {}", compilationCreateDto);
        CompilationDto result = compilationService.create(compilationMapper.toCompilation(compilationCreateDto));
        log.info("Create compilation success");
        return result;
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление подборки")
    public void deleteById(@PathVariable Long compId) {
        log.info("Delete compilation {}", compId);
        compilationService.deleteById(compId);
        log.info("Delete compilation success");
    }

    @PatchMapping("/{compId}")
    @Operation(summary = "Обновить информацию о подборке")
    public CompilationDto update(@PathVariable Long compId,
                                 @Valid @RequestBody CompilationUpdateDto compilationUpdateDto) {
        log.info("Update compilation {}, data {}", compId, compilationUpdateDto);
        CompilationDto result = compilationService.update(compId, compilationMapper.toCompilation(compilationUpdateDto));
        log.info("Update compilation success");
        return result;
    }
}
