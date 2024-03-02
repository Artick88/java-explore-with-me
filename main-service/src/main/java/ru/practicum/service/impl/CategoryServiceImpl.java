package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.entity.Category;
import ru.practicum.service.CategoryService;
import ru.practicum.storage.repository.CategoryRepository;
import ru.practicum.storage.repository.EventRepository;
import ru.practicum.utils.enums.ReasonExceptionEnum;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Category create(Category category) {
        checkDoubleCategoryName(category);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteById(Long catId) {
        //TODO: проверка на отсутствие событий
        checkExistsCategoryById(catId);

        if (eventRepository.existsByCategory_Id(catId)) {
            throw new ConflictException("Exists event", ReasonExceptionEnum.CONFLICT.getReason());
        }

        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public Category update(Long catId, Category category) {
        Category findCategory = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Not found category by id %d", catId),
                        ReasonExceptionEnum.NOT_FOUND.getReason()));

        if (findCategory.getName().equals(category.getName())) {
            return findCategory;
        }

        checkDoubleCategoryName(category);

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

    private void checkDoubleCategoryName(Category category) {
        if (category.getName() != null && categoryRepository.existsByName(category.getName())) {
            throw new ConflictException("Double name category", ReasonExceptionEnum.CONFLICT.getReason());
        }
    }
}
