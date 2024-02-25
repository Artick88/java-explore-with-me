package ru.practicum.service;

import ru.practicum.model.entity.Request;
import ru.practicum.model.UpdateRequest;

import java.util.List;

public interface RequestService {

    Request create(Long userId, Long eventId);

    Request cancelRequest(Long userId, Long requestId);

    List<Request> getAllByUserId(Long userId);

    List<Request> getAllByEventId(Long userId, Long eventId);

    List<Request> updateStatus(Long userId, Long eventId, UpdateRequest updateRequest);

    Integer getCountActiveRequestOnEventById(Long eventId);
}
