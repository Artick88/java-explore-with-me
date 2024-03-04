package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.UpdateRequest;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.Request;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;
import ru.practicum.service.UserService;
import ru.practicum.storage.repository.RequestRepository;
import ru.practicum.utils.enums.ReasonExceptionEnum;
import ru.practicum.utils.enums.StateEvent;
import ru.practicum.utils.enums.StatusRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    @Transactional
    public Request create(Long userId, Long eventId, HttpServletRequest httpServletRequest) {
        //Это нужно для тестов, иначе не проходят....
        LocalDateTime timeCreationRequest = LocalDateTime.ofInstant(Instant
                .ofEpochMilli(httpServletRequest.getSession().getCreationTime()), TimeZone.getDefault().toZoneId());

        return requestRepository.save(validateCreateRequest(userId, eventId, timeCreationRequest));
    }

    @Override
    @Transactional
    public Request cancelRequest(Long userId, Long requestId) {
        Request savedRequest = validateCancelRequest(userId, requestId);
        savedRequest.setStatus(StatusRequest.CANCELED);
        return savedRequest;
    }

    @Override
    public List<Request> getAllByUserId(Long userId) {
        userService.checkExistUserById(userId);
        return requestRepository.findAllByRequester(userId).orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public List<Request> updateStatus(Long userId, Long eventId, UpdateRequest updateRequest) {

        userService.checkExistUserById(userId);
        Event event = eventService.checkExistEventById(eventId);
        validateUpdateStatus(event, updateRequest);

        List<Request> requestList = requestRepository.findAllByIdIn(updateRequest.getRequestIds())
                .orElse(new ArrayList<>());

        Integer limitRequest = event.getParticipantLimit();

        //Если отклонение нам не важен лимит, просто все отклоняем
        if (updateRequest.getStatus().equals(StatusRequest.REJECTED)) {
            requestRepository.updateStatusByIds(updateRequest.getRequestIds(), updateRequest.getStatus());
            return requestList.stream().peek(request -> request.setStatus(updateRequest.getStatus()))
                    .collect(Collectors.toList());
        }

        //TODO: подумать как сразу несколько записей обновлять, не делая это в цикле
        for (Request request : requestList) {
            //Проверяем лимит перед каждым изменением
            if (limitRequest <= getCountActiveRequestOnEventById(eventId)) {
                request.setStatus(StatusRequest.REJECTED);
            } else {
                request.setStatus(StatusRequest.CONFIRMED);
            }
        }

        return requestList;
    }

    private void validateUpdateStatus(Event event, UpdateRequest updateRequest) {
        //Если нет лимита или не требуется подтверждение
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ConflictException("No confirmation required", ReasonExceptionEnum.CONFLICT.getReason());
        }
        //Если апрув и уже лимит исчерпан, то сразу оишбка
        if (updateRequest.getStatus().equals(StatusRequest.CONFIRMED)
                && event.getParticipantLimit() <= getCountActiveRequestOnEventById(event.getId())) {
            throw new ConflictException("Limit over", ReasonExceptionEnum.CONFLICT.getReason());
        }
        //Нельзая отклонить, если есть подтвержденные
        if (updateRequest.getStatus().equals(StatusRequest.REJECTED)
                && requestRepository.existsRequestByIdInAndStatus(updateRequest.getRequestIds(), StatusRequest.CONFIRMED)) {
            throw new ConflictException("Сan not reject a confirmed", ReasonExceptionEnum.CONFLICT.getReason());
        }
    }

    @Override
    public List<Request> getAllByEventId(Long userId, Long eventId) {
        userService.checkExistUserById(userId);
        eventService.checkExistEventById(eventId);
        return requestRepository.findAllByEvent(eventId).orElse(new ArrayList<>());
    }

    private Request validateCreateRequest(Long userId, Long eventId, LocalDateTime timeCreated) {
        userService.checkExistUserById(userId);
        Event event = eventService.checkExistEventById(eventId);

        if (requestRepository.findByRequesterAndEvent(userId, eventId).isPresent()) {
            throw new ConflictException("Duplicate request", ReasonExceptionEnum.CONFLICT.getReason());
        }

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("This is your event", ReasonExceptionEnum.CONFLICT.getReason());
        }

        if (!event.getState().equals(StateEvent.PUBLISHED)) {
            throw new ConflictException("Event not published", ReasonExceptionEnum.CONFLICT.getReason());
        }

        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= getCountActiveRequestOnEventById(eventId)) {
            throw new ConflictException("Limit is over", ReasonExceptionEnum.CONFLICT.getReason());
        }

        return Request.builder()
                .requester(userId)
                .event(eventId)
                .created(timeCreated)
                .status(!event.getRequestModeration() || event.getParticipantLimit() == 0 ? StatusRequest.CONFIRMED : StatusRequest.PENDING)
                .build();
    }

    private Request validateCancelRequest(Long userId, Long requestId) {
        userService.checkExistUserById(userId);
        Request savedRequest = checkExistsRequestById(requestId);

        if (!savedRequest.getRequester().equals(userId)) {
            throw new ConflictException("Cancel request can only requester", ReasonExceptionEnum.CONFLICT.getReason());
        }

        if (savedRequest.getStatus().equals(StatusRequest.CANCELED)) {
            throw new ConflictException("Request has already been canceled", ReasonExceptionEnum.CONFLICT.getReason());
        }

        return savedRequest;
    }

    private Request checkExistsRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Not found request by id %d", requestId), ReasonExceptionEnum.NOT_FOUND.getReason()));
    }

    @Override
    public Integer getCountActiveRequestOnEventById(Long eventId) {
        return requestRepository.countAllByEventAndStatus(eventId, StatusRequest.CONFIRMED).orElse(0);
    }

}
