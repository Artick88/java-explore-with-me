package ru.practicum.mapper;

import org.mapstruct.*;
import ru.practicum.dto.event.EventUpdateAdminDto;
import ru.practicum.dto.event.EventUpdateUserDto;
import ru.practicum.model.entity.Event;
import ru.practicum.utils.enums.StateEvent;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UpdateEventMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeToEvent(Event eventFrom, @MappingTarget Event eventTo);

    @Mapping(source = ".", target = "state", qualifiedByName = "actionUserToStateNamed")
    @Mapping(source = "category", target = "category.id", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Event toEvent(EventUpdateUserDto eventUpdateUserDto);

    @Mapping(source = ".", target = "state", qualifiedByName = "actionAdminToStateNamed")
    @Mapping(source = "category", target = "category.id", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Event toEvent(EventUpdateAdminDto eventUpdateAdminDto);

    @Named("actionAdminToStateNamed")
    default StateEvent actionAdminToState(EventUpdateAdminDto eventUpdateAdminDto) {
        if (eventUpdateAdminDto.getStateAction() == null) {
            return null;
        }
        return eventUpdateAdminDto.getStateAction().getStateEvent();
    }

    @Named("actionUserToStateNamed")
    default StateEvent actionUserToState(EventUpdateUserDto eventUpdateUserDto) {
        if (eventUpdateUserDto.getStateAction() == null) {
            return null;
        }
        return eventUpdateUserDto.getStateAction().getStateEvent();
    }

    @AfterMapping
    default void categoryToEntity(@MappingTarget Event event) {
        if (event.getCategory().getId() == null) {
            event.setCategory(null);
        }
    }

}
