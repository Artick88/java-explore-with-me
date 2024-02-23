package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.Event;
import ru.practicum.model.Stats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    Event toEvent(EventDto eventDto);

    List<StatsDto> toStatsDto(List<Stats> stats);
}
