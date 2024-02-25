package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.model.entity.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    void deleteById(Long catId);

    Category update(Long catId, Category category);

    Category getById(Long catId);

    List<Category> getAll(Pageable page);

    Category checkExistsCategoryById(Long catId);
}
