package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.entity.Compilation;
import ru.practicum.model.entity.Event;
import ru.practicum.service.CompilationService;
import ru.practicum.service.EventService;
import ru.practicum.storage.repository.CompilationRepository;
import ru.practicum.storage.specification.CompilationSpecification;
import ru.practicum.utils.enums.ReasonExceptionEnum;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto create(Compilation compilation) {

        List<Event> realEvents = prepareRealEvent(compilation);
        compilation.setEvents(realEvents.stream().map(Event::getId).collect(Collectors.toSet()));

        compilationRepository.save(compilation);

        return prepareCompilationDto(compilation, realEvents);
    }

    @Override
    public CompilationDto update(Long compId, Compilation compilation) {

        Compilation savedCompilation = checkExistsCompilation(compId);

        List<Event> realEvents = prepareRealEvent(compilation);
        compilation.setEvents(realEvents.stream().map(Event::getId).collect(Collectors.toSet()));

        compilationMapper.toMergeCompilation(compilation, savedCompilation);

        compilationRepository.save(savedCompilation);

        return prepareCompilationDto(savedCompilation, realEvents);
    }

    @Override
    public void deleteById(Long compId) {
        checkExistsCompilation(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto getById(Long compId) {
        Compilation compilation = checkExistsCompilation(compId);
        List<Event> eventList = prepareRealEvent(compilation);
        return prepareCompilationDto(compilation, eventList);
    }

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Pageable page) {

        List<Compilation> compilationList = compilationRepository.findAll(CompilationSpecification.hasPinned(pinned), page)
                .stream().collect(Collectors.toList());

        List<Long> eventListIds = new ArrayList<>();

        //Вытищим все ид событий, чтобы сделать "один" запрос в базу
        for (Set<Long> compilation : compilationList.stream().map(Compilation::getEvents).collect(Collectors.toList())) {
            eventListIds.addAll(compilation);
        }

        Map<Long, Event> eventMap = eventService.getAllByIds(eventListIds).stream()
                .collect(Collectors.toMap(Event::getId, event -> event));

        return compilationList.stream()
                .map(comp -> {
                    List<Event> eventList = comp.getEvents().stream()
                            .map(eventMap::get)
                            .collect(Collectors.toList());
                    return prepareCompilationDto(comp, eventList);
                })
                .collect(Collectors.toList());
    }

    private List<Event> prepareRealEvent(Compilation compilation) {
        List<Event> eventList = new ArrayList<>();

        if (compilation.getEvents() != null) {
            eventList = eventService.getAllByIds(new ArrayList<>(compilation.getEvents()));
        }

        return eventList;
    }

    private CompilationDto prepareCompilationDto(Compilation compilation, List<Event> eventList) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(new HashSet<>(eventMapper.toEventShorDto(eventList)))
                .build();
    }

    private Compilation checkExistsCompilation(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Not found compilation by id %d", compId), ReasonExceptionEnum.NOT_FOUND.getReason()));
    }
}
