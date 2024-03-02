package ru.practicum.storage.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.practicum.model.entity.Compilation;

@Component
public class CompilationSpecification {

    public static Specification<Compilation> hasPinned(Boolean pinned) {
        return ((root, query, criteriaBuilder) -> pinned == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("pinned"), pinned));
    }

}
