package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.model.EventStat;
import ru.practicum.model.entity.Event;
import ru.practicum.utils.enums.SortEvent;
import ru.practicum.utils.enums.StateEvent;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<Event> getAllByIds(List<Long> eventIds);

    Event create(Long userId, Event event);

    Event update(Long userId, Long eventId, Event event);

    Event updateIsAdmin(Long eventId, Event event);

    Event getUserEventById(Long userId, Long eventId);

    List<Event> getUserEvents(Long userid, Pageable page);

    Event checkExistEventById(Long eventId);

    Event getById(Long eventId, HttpServletRequest httpServletRequest);

    List<EventStat> search(HttpServletRequest httpServletRequest, String text, List<Long> categories, Boolean paid,
                           LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, SortEvent sort, Pageable page);

    List<EventStat> searchAdmin(List<Long> users, List<StateEvent> states, List<Long> categories, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Pageable page);

}
