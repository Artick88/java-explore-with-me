package ru.practicum.storage.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.practicum.model.entity.User;

import java.util.List;

@Component
public class UserSpecification {
    public static Specification<User> hasIds(@Nullable List<Long> ids) {
        return ((root, query, criteriaBuilder) ->
                ids == null || ids.isEmpty() ? criteriaBuilder.conjunction() : root.get("id").in(ids));
    }

}
