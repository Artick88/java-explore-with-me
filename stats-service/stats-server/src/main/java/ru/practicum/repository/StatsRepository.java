package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Event;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT new ru.practicum.model.Stats(s.app, s.uri, count(s.ip)) " +
            "FROM Event as s " +
            "WHERE s.uri IN (?3) AND s.timestamp BETWEEN (?1) AND (?2) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(s.ip) DESC")
    List<Stats> findAllByStatsWithUriNotUnique(LocalDateTime startDate, LocalDateTime endDate, List<String> uri);

    @Query("SELECT new ru.practicum.model.Stats(s.app, s.uri, count(distinct s.ip)) " +
            "FROM Event as s " +
            "WHERE s.uri IN (?3) AND s.timestamp BETWEEN (?1) AND (?2) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(distinct s.ip) DESC")
    List<Stats> findAllByStatsWithUriUnique(LocalDateTime startDate, LocalDateTime endDate, List<String> uri);

    @Query("SELECT new ru.practicum.model.Stats(s.app, s.uri, count(s.ip)) " +
            "FROM Event as s " +
            "WHERE s.timestamp BETWEEN (?1) AND (?2) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(s.ip) DESC")
    List<Stats> findAllByStatsWithoutUriNotUnique(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT new ru.practicum.model.Stats(s.app, s.uri, count(distinct s.ip)) " +
            "FROM Event as s " +
            "WHERE s.timestamp BETWEEN (?1) AND (?2) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(distinct s.ip) DESC")
    List<Stats> findAllByStatsWithoutUriUnique(LocalDateTime startDate, LocalDateTime endDate);

}
