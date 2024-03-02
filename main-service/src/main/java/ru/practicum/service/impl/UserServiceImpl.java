package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.entity.User;
import ru.practicum.service.UserService;
import ru.practicum.storage.repository.UserRepository;
import ru.practicum.storage.specification.UserSpecification;
import ru.practicum.utils.enums.ReasonExceptionEnum;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
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

    private void checkDoubleUserEmail(User user) {
        if (user.getName() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Double email", ReasonExceptionEnum.CONFLICT.getReason());
        }
    }

}
