package ru.practicum.controller.publ;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.service.CategoryService;
import ru.practicum.utils.PaginationCustom;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Public: Категории", description = "Публичный API для работы с категориями")
public class PublicCategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    @Operation(summary = "Получение категорий")
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get all category from {} size {}", from, size);
        List<CategoryDto> result = categoryMapper.toCategoryDto(categoryService.getAll(PaginationCustom.getPageable(from, size)));
        log.info("Get all success size {}", result.size());
        return result;
    }

    @GetMapping("/{catId}")
    @Operation(summary = "Получение категорий")
    public CategoryDto get(@PathVariable Long catId) {
        log.info("Get category by id {}", catId);
        CategoryDto result = categoryMapper.toCategoryDto(categoryService.getById(catId));
        log.info("Get category success");
        return result;
    }
}
