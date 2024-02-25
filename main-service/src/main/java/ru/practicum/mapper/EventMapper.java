package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.dto.event.*;
import ru.practicum.model.entity.Event;
import ru.practicum.utils.enums.StateActionEvent;
import ru.practicum.utils.enums.StateEvent;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, StateEvent.class, StateActionEvent.class},
        uses = CategoryMapper.class)
public interface EventMapper {

    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now())")
    @Mapping(source = "location.lat", target = "lat")
    @Mapping(source = "location.lon", target = "lon")
    @Mapping(source = "category", target = "category.id")
    @Mapping(target = "state", expression = "java(StateEvent.PENDING)")
    Event toEvent(EventCreateDto eventCreateDto);

    @Mapping(source = "location.lat", target = "lat")
    @Mapping(source = "location.lon", target = "lon")
    @Mapping(source = "category", target = "category.id")
    Event toEvent(EventUpdateUserDto eventUpdateUserDto);

    @Mapping(source = "lat", target = "location.lat")
    @Mapping(source = "lon", target = "location.lon")
    EventFullDto toEventFullDto(Event event);

    List<EventFullDto> toEventFullDto(List<Event> event);

    List<EventShortDto> toEventShorDto(List<Event> events);

    EventShortDto toEventShorDto(Event event);

    @Mapping(source = ".", target = "state", qualifiedByName = "actionToStateNamed")
    @Mapping(source = "category", target = "category.id")
    Event toEvent(EventUpdateAdminDto eventUpdateAdminDto);

    @Named("actionToStateNamed")
    default StateEvent ActionToState(EventUpdateAdminDto eventUpdateAdminDto) {
        if (eventUpdateAdminDto.getStateAction() == null) {
            return null;
        }
        return eventUpdateAdminDto.getStateAction().getStateEvent();
    }
}
