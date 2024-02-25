package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.dto.EventDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.entity.Event;
import ru.practicum.service.CategoryService;
import ru.practicum.service.EventService;
import ru.practicum.service.UserService;
import ru.practicum.storage.repository.EventRepository;
import ru.practicum.storage.specification.EventSpecification;
import ru.practicum.utils.enums.ReasonExceptionEnum;
import ru.practicum.utils.enums.SortEvent;
import ru.practicum.utils.enums.StateEvent;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Value("${stats.service.app.name}")
    private String appName;

    private static final int MIN_HOURS_DIFF_FOR_EVENT_DATE_FROM_CURRENT_DATE = 2;
    private static final int ADMIN_MIN_HOURS_DIFF_FOR_PUBLISHED_DATE_FROM_EVENT_DATE = 1;

    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final StatClient statClient;

    @Override
    public List<Event> getAllByIds(List<Long> eventIds) {
        return eventRepository.findAllById(eventIds);
    }

    @Override
    public Event create(Long userId, Event event) {
        return eventRepository.save(validateCreateEvent(event, userId));
    }

    @Override
    public Event update(Long userId, Long eventId, Event event) {
        event.setId(eventId);
        return eventRepository.save(validateUpdateEvent(event, userId));
    }

    @Override
    public Event updateIsAdmin(Long eventId, Event event) {
        event.setId(eventId);
        return eventRepository.save(validateAdminUpdateEvent(event));
    }


    @Override
    public Event getUserEventById(Long userId, Long eventId) {
        userService.checkExistUserById(userId);
        return checkExistEventById(eventId);
    }

    @Override
    public List<Event> getUserEvents(Long userid, Pageable page) {
        userService.checkExistUserById(userid);
        return eventRepository.findAllByInitiator_Id(userid, page).orElse(new ArrayList<>());
    }

    private Event validateCreateEvent(Event event, Long userId) {
        event.setInitiator(userService.checkExistUserById(userId));
        event.setCategory(categoryService.checkExistsCategoryById(event.getCategory().getId()));
        checkEventDate(event);
        return event;
    }

    private Event validateUpdateEvent(Event event, Long userId) {
        Event savedEvent = checkExistEventById(event.getId());
        event.setInitiator(userService.checkExistUserById(userId));
        event.setCategory(categoryService.checkExistsCategoryById(event.getCategory().getId()));
        checkEventDate(event);
        checkEventInitiator(savedEvent, userId);
        checkEventState(savedEvent, false);

        return event;
    }

    private Event validateAdminUpdateEvent(Event event) {
        Event savedEvent = checkExistEventById(event.getId());
        if (event.getCategory().getId() != null) {
            event.setCategory(categoryService.checkExistsCategoryById(event.getCategory().getId()));
        }
        checkPublishedDate(event);
        checkEventState(savedEvent, true);
        return event;
    }

    @Override
    public Event checkExistEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Not found event by id %d", eventId),
                        ReasonExceptionEnum.NOT_FOUND.getReason()));
    }

    @Override
    public Event getById(Long eventId, HttpServletRequest httpServletRequest) {
        Event event = eventRepository.findByIdAndState(eventId, StateEvent.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Not found published event", ReasonExceptionEnum.NOT_FOUND.getReason()));

        //Добавим в статистику текущий вызов запрос и обновим счетчик
        addStat(httpServletRequest);
        event.setCountViews(Optional.ofNullable(event.getCountViews()).orElse(0) + 1);

        return event;
    }

    @Override
    public List<Event> search(HttpServletRequest httpServletRequest, String text, List<Long> categories, Boolean paid,
                              LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, SortEvent sort,
                              Pageable page) {

        Specification<Event> specificationSearch = Specification
                .where(EventSpecification.hasText(text))
                .and(EventSpecification.hasCategories(categories))
                .and(EventSpecification.isPaid(paid))
                .and(EventSpecification.hasRangeDate(rangeStart, rangeEnd))
                .and(EventSpecification.needCheckLimit(onlyAvailable));

        //Добавим в статистику
        addStat(httpServletRequest);

        return eventRepository.findAll(EventSpecification.needSort(specificationSearch, sort), page)
                .stream().collect(Collectors.toList());
    }

    @Override
    public List<Event> searchAdmin(List<Long> users, List<StateEvent> states, List<Long> categories, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, Pageable page) {

        Specification<Event> specificationSearch = Specification
                .where(EventSpecification.hasUsers(users))
                .and(EventSpecification.hasStates(states))
                .and(EventSpecification.hasCategories(categories))
                .and(EventSpecification.hasRangeDateStart(rangeStart))
                .and(EventSpecification.hasRangeDateEnd(rangeEnd));

        return eventRepository.findAll(specificationSearch, page).stream().collect(Collectors.toList());
    }

    private void checkEventDate(Event event) {
        //Минимальная возможная дата события через 2 часа от текущего времени
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now()
                .plusHours(MIN_HOURS_DIFF_FOR_EVENT_DATE_FROM_CURRENT_DATE))) {
            throw new ConflictException("Minimum possible event date 2 hours from current time",
                    ReasonExceptionEnum.CONFLICT.getReason());
        }
    }

    private void checkPublishedDate(Event event) {
        //Минимальная возможная дата публикации за час до даты события
        if (event.getEventDate() != null && event.getPublishedOn() != null && event.getPublishedOn()
                .isBefore(event.getEventDate().plusHours(ADMIN_MIN_HOURS_DIFF_FOR_PUBLISHED_DATE_FROM_EVENT_DATE))) {
            throw new ConflictException("Minimum possible published date 1 hour for event time",
                    ReasonExceptionEnum.CONFLICT.getReason());
        }
    }

    private void checkEventInitiator(Event event, Long userId) {
        //Изменять событие может только его инициатор
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Change event can only initiator", ReasonExceptionEnum.CONFLICT.getReason());
        }
    }

    private void checkEventState(Event event, boolean isAdmin) {
        if (isAdmin && !event.getState().equals(StateEvent.PENDING)) {
            throw new ConflictException("Admin edit state can only pending", ReasonExceptionEnum.CONFLICT.getReason());
        } else if (!event.getState().equals(StateEvent.CANCELED) && !event.getState().equals(StateEvent.PENDING)) {
            throw new ConflictException("Editing is not possible in this state", ReasonExceptionEnum.CONFLICT.getReason());
        }
    }

    private void addStat(HttpServletRequest httpServletRequest) {
        statClient.save(EventDto.builder()
                .ip(httpServletRequest.getRemoteAddr())
                .app(appName)
                .uri(httpServletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build());
    }

}
