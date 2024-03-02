package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.model.entity.Compilation;

import java.util.List;

public interface CompilationService {

    CompilationDto create(Compilation compilation);

    CompilationDto update(Long compId, Compilation compilation);

    void deleteById(Long compId);

    CompilationDto getById(Long compId);

    List<CompilationDto> getAll(Boolean pinned, Pageable page);

}
