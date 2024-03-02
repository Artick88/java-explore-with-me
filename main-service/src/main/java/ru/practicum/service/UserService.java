package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import ru.practicum.model.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);

    List<User> getByIds(@Nullable List<Long> ids, Pageable page);

    void deleteById(Long userId);

    User checkExistUserById(Long userId);

    void updateRatingUser(User user);

}
