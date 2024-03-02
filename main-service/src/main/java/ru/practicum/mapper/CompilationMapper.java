package ru.practicum.mapper;

import org.mapstruct.*;
import ru.practicum.dto.compilation.CompilationCreateDto;
import ru.practicum.dto.compilation.CompilationUpdateDto;
import ru.practicum.model.entity.Compilation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompilationMapper {
    @Mapping(source = "pinned", target = "pinned", defaultValue = "false")
    Compilation toCompilation(CompilationCreateDto compilationCreateDto);

    Compilation toCompilation(CompilationUpdateDto compilationUpdateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toMergeCompilation(Compilation compilationFrom, @MappingTarget Compilation compilationTo);
}
