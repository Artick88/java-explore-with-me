package ru.practicum.service;

import ru.practicum.model.Event;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    void save(Event event);

    List<Stats> get(LocalDateTime startDate, LocalDateTime endDate, List<String> uris, boolean unique);
}
