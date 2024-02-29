package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.Event;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface StatsMapper {

    @Mapping(source = "timestamp", target = "timestamp", qualifiedByName = "dateTimeParse")
    Event toEvent(EventDto eventDto);

    List<StatsDto> toStatsDto(List<Stats> stats);

    @Named("dateTimeParse")
    default LocalDateTime dateTimeParse(String timestamp) {
        return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
