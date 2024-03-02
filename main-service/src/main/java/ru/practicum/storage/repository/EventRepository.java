package ru.practicum.storage.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.model.entity.Event;
import ru.practicum.utils.enums.StateEvent;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Optional<List<Event>> findAllByInitiator_Id(Long userId, Pageable page);

    Optional<Event> findByIdAndState(Long eventId, StateEvent state);

    boolean existsByCategory_Id(Long id);

}
