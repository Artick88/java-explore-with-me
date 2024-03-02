package ru.practicum.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.entity.EventEvaluate;
import ru.practicum.utils.enums.EvaluateEventType;

import java.util.Optional;

public interface EventEvaluateRepository extends JpaRepository<EventEvaluate, Long> {

    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    Optional<EventEvaluate> findByUserIdAndEventIdAndEvaluate(Long userId, Long eventId, EvaluateEventType operation);

}