package ru.practicum.storage.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.practicum.model.entity.Event;
import ru.practicum.utils.enums.SortEvent;
import ru.practicum.utils.enums.StateEvent;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EventSpecification {

    public static Specification<Event> needSort(Specification<Event> specification, SortEvent sort) {

        if (sort == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get(sort.getNameFiled())));
            return specification.toPredicate(root, query, criteriaBuilder);
        };
    }

    public static Specification<Event> needCheckLimit(Boolean onlyAvailable) {
        return (root, query, criteriaBuilder) -> !onlyAvailable ? criteriaBuilder.conjunction()
                : criteriaBuilder.lessThan(root.get("countConfirmedRequests"), root.get("participantLimit"));
    }

    public static Specification<Event> hasRangeDate(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return (root, query, criteriaBuilder) -> rangeStart == null || rangeEnd == null ?
                criteriaBuilder.greaterThan(root.get("eventDate"), LocalDateTime.now())
                : criteriaBuilder.between(root.get("eventDate"), rangeStart, rangeEnd);
    }

    public static Specification<Event> hasRangeDateStart(LocalDateTime rangeStart) {
        return (root, query, criteriaBuilder) -> rangeStart == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.greaterThan(root.get("eventDate"), rangeStart);
    }

    public static Specification<Event> hasRangeDateEnd(LocalDateTime rangeEnd) {
        return (root, query, criteriaBuilder) -> rangeEnd == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.lessThan(root.get("eventDate"), rangeEnd);
    }

    public static Specification<Event> isPaid(Boolean paid) {
        return ((root, query, criteriaBuilder) ->
                paid == null ? criteriaBuilder.conjunction() : root.get("paid").in(paid));
    }

    public static Specification<Event> hasCategories(List<Long> categories) {
        return ((root, query, criteriaBuilder) ->
                categories == null || categories.isEmpty() ? criteriaBuilder.conjunction() : root.get("category").in(categories));
    }

    public static Specification<Event> hasUsers(List<Long> users) {
        return ((root, query, criteriaBuilder) ->
                users == null || users.isEmpty() ? criteriaBuilder.conjunction() : root.get("initiator").in(users));
    }

    public static Specification<Event> hasStates(List<StateEvent> states) {
        return ((root, query, criteriaBuilder) ->
                states == null || states.isEmpty() ? criteriaBuilder.conjunction() : root.get("state").in(states));
    }

    public static Specification<Event> hasText(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }

        return Specification
                .where(like(text, "annotation"))
                .or(like(text, "description"));
    }

    private static Specification<Event> like(String text, String field) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + text.toLowerCase() + "%"));
    }

}
