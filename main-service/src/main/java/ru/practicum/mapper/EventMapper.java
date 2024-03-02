package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.event.EventCreateDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.model.EventStat;
import ru.practicum.model.entity.Event;
import ru.practicum.utils.enums.StateAdminActionEvent;
import ru.practicum.utils.enums.StateEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, StateEvent.class, StateAdminActionEvent.class},
        uses = CategoryMapper.class)
public interface EventMapper {

    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now())")
    @Mapping(source = "category", target = "category.id")
    @Mapping(target = "state", expression = "java(StateEvent.PENDING)")
    @Mapping(target = "paid", defaultValue = "false")
    @Mapping(target = "participantLimit", defaultValue = "0")
    @Mapping(target = "requestModeration", defaultValue = "true")
    Event toEvent(EventCreateDto eventCreateDto);

    EventFullDto toEventFullDto(Event event);

    List<EventFullDto> toEventFullDto(List<Event> events);

    List<EventShortDto> toEventShorDto(List<Event> events);

    EventShortDto toEventShorDto(Event event);

    default EventFullDto toEventStatFullDto(EventStat eventStat) {
        if (eventStat == null) {
            return null;
        }

        EventFullDto eventFullDto = toEventFullDto(eventStat.getEvent());
        eventFullDto.setConfirmedRequests(eventStat.getConfirmedRequests());

        return eventFullDto;
    }

    default List<EventFullDto> toEventStatFullDto(List<EventStat> eventStats) {
        return eventStats.stream().map(this::toEventStatFullDto).collect(Collectors.toList());
    }

    default EventShortDto toEventStatShortDto(EventStat eventStat) {
        if (eventStat == null) {
            return null;
        }

        EventShortDto eventShortDto = toEventShorDto(eventStat.getEvent());
        eventShortDto.setConfirmedRequests(eventStat.getConfirmedRequests());

        return eventShortDto;
    }

    default List<EventShortDto> toEventStatShortDto(List<EventStat> eventStats) {
        return eventStats.stream().map(this::toEventStatShortDto).collect(Collectors.toList());
    }
}
