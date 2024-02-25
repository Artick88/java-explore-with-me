package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.compilation.CompilationCreateDto;
import ru.practicum.dto.compilation.CompilationUpdateDto;
import ru.practicum.model.entity.Compilation;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    Compilation toCompilation(CompilationCreateDto compilationCreateDto);

    Compilation toCompilation(CompilationUpdateDto compilationUpdateDto);

}
