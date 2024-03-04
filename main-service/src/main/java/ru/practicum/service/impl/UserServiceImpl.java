package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.User;
import ru.practicum.service.UserService;
import ru.practicum.storage.repository.EventRepository;
import ru.practicum.storage.repository.UserRepository;
import ru.practicum.storage.specification.UserSpecification;
import ru.practicum.utils.enums.ReasonExceptionEnum;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public User create(User user) {
        checkDoubleUserEmail(user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getByIds(@Nullable List<Long> ids, Pageable page) {

        Page<User> pageResult = userRepository.findAll(UserSpecification.hasIds(ids), page);

        return pageResult.stream().collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        checkExistUserById(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public User checkExistUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Not found user by id %d", userId),
                        ReasonExceptionEnum.NOT_FOUND.getReason()));
    }

    @Override
    @Transactional
    public void updateRatingUser(User user) {
        List<Event> events = eventRepository.findAllByInitiator_Id(user.getId(), null).orElse(Collections.emptyList());

        if (events.isEmpty()) {
            return;
        }

        Float sumRatingEvent = events.stream()
                .map(Event::getRating)
                .filter(Objects::nonNull)
                .reduce(Float::sum)
                .orElse(0F);

        //Находим среднее по оцененным событиям
        Float avgRatingUser = sumRatingEvent / events.stream().filter(event -> event.getRating() != null).count();

        user.setRating(avgRatingUser);
    }

    private void checkDoubleUserEmail(User user) {
        if (user.getName() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Double email", ReasonExceptionEnum.CONFLICT.getReason());
        }
    }

}
