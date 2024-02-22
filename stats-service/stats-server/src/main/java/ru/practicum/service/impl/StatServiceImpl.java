package ru.practicum.service.impl;

import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.BadRequestException;
import ru.practicum.model.Event;
import ru.practicum.model.Stats;
import ru.practicum.repository.StatsRepository;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public void save(Event event) {
        statsRepository.save(event);
    }

    @Override
    public List<Stats> get(LocalDateTime startDate, LocalDateTime endDate, @Nullable List<String> uris, boolean unique) {

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Некорректные даты фильтрации");
        }

        if ((uris == null || uris.isEmpty()) && unique) {
            return statsRepository.findAllByStatsWithoutUriUnique(startDate, endDate);
        } else if (uris == null || uris.isEmpty()) {
            return statsRepository.findAllByStatsWithoutUriNotUnique(startDate, endDate);
        } else if (unique) {
            return statsRepository.findAllByStatsWithUriUnique(startDate, endDate, uris);
        } else {
            return statsRepository.findAllByStatsWithUriNotUnique(startDate, endDate, uris);
        }
    }
}
