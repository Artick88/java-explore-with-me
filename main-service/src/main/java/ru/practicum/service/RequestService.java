package ru.practicum.service;

import ru.practicum.model.UpdateRequest;
import ru.practicum.model.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RequestService {

    Request create(Long userId, Long eventId, HttpServletRequest httpServletRequest);

    Request cancelRequest(Long userId, Long requestId);

    List<Request> getAllByUserId(Long userId);

    List<Request> getAllByEventId(Long userId, Long eventId);

    List<Request> updateStatus(Long userId, Long eventId, UpdateRequest updateRequest);

    Integer getCountActiveRequestOnEventById(Long eventId);
}
