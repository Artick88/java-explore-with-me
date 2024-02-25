package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.entity.Category;
import ru.practicum.service.CategoryService;
import ru.practicum.storage.repository.CategoryRepository;
import ru.practicum.utils.enums.ReasonExceptionEnum;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long catId) {
        //TODO: проверка на отсутствие событий
        checkExistsCategoryById(catId);
        categoryRepository.deleteById(catId);
    }

    @Override
    public Category update(Long catId, Category category) {
        Category findCategory = checkExistsCategoryById(catId);
        findCategory.setName(category.getName());
        return findCategory;
    }

    @Override
    public Category getById(Long catId) {
        return checkExistsCategoryById(catId);
    }

    @Override
    public List<Category> getAll(Pageable page) {
        return categoryRepository.findAll(page).stream().collect(Collectors.toList());
    }

    @Override
    public Category checkExistsCategoryById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Not found category by id %d", catId),
                        ReasonExceptionEnum.NOT_FOUND.getReason()));
    }
}
