package ru.practicum.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.CategoryRequestDto;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${default.url.path.admin}/categories")
@Tag(name = "Admin: Категории", description = "API для работы с категориями")
public class AdminCategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление новой категории")
    public CategoryDto create(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        log.info("Create category {}", categoryRequestDto);
        CategoryDto categoryDto = categoryMapper.toCategoryDto(categoryService.create(categoryMapper
                .toCategory(categoryRequestDto)));
        log.info("Create category success");
        return categoryDto;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление категории")
    public void deleteById(@PathVariable Long catId) {
        log.info("Delete category with id {}", catId);
        categoryService.deleteById(catId);
        log.info("Delete category success");
    }

    @PatchMapping("/{catId}")
    @Operation(summary = "Изменение категории")
    public CategoryDto update(@PathVariable Long catId,
                              @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        log.info("Update category with id {}, data {}", catId, categoryRequestDto);
        CategoryDto categoryDto = categoryMapper.toCategoryDto(categoryService.update(catId,
                categoryMapper.toCategory(categoryRequestDto)));
        log.info("Update category success");
        return categoryDto;
    }

}
