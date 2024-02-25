package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.Request;
import ru.practicum.model.UpdateRequest;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;
import ru.practicum.service.UserService;
import ru.practicum.storage.repository.RequestRepository;
import ru.practicum.utils.enums.ReasonExceptionEnum;
import ru.practicum.utils.enums.StateEvent;
import ru.practicum.utils.enums.StatusRequest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    public Request create(Long userId, Long eventId) {
        return requestRepository.save(validateCreateRequest(userId, eventId));
    }

    @Override
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

    @Transactional
    @Override
    public List<Request> updateStatus(Long userId, Long eventId, UpdateRequest updateRequest) {

        userService.checkExistUserById(userId);
        Event event = eventService.checkExistEventById(eventId);
        Integer limitRequest = event.getParticipantLimit();

        //Если нет лимита или не требуется подтверждение
        if (event.getRequestModeration() || limitRequest == 0) {
            throw new ConflictException("No confirmation required", ReasonExceptionEnum.CONFLICT.getReason());
        }

        //Если отклонение нам не важен лимит, просто все отклоняем
        if (updateRequest.getStatus().equals(StatusRequest.REJECTED)) {
            requestRepository.updateStatusByIds(updateRequest.getRequestIds(), updateRequest.getStatus());
            return requestRepository.findAllByIdIn(updateRequest.getRequestIds()).orElse(new ArrayList<>());
        }

        //Получим все заявки в ожидании
        List<Request> requestList = requestRepository.findAllByIdInAndStatus(updateRequest.getRequestIds(), StatusRequest.PENDING)
                .orElse(new ArrayList<>());

        //TODO: подумать как сразу несколько записей обновлять, не делая это в цикле
        //В цикле обновляем статусы
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

    @Override
    public List<Request> getAllByEventId(Long userId, Long eventId) {
        userService.checkExistUserById(userId);
        eventService.checkExistEventById(eventId);
        return requestRepository.findAllByEvent(eventId).orElse(new ArrayList<>());
    }

    private Request validateCreateRequest(Long userId, Long eventId) {
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
                .created(LocalDateTime.now())
                .status(event.getRequestModeration() ? StatusRequest.PENDING : StatusRequest.CONFIRMED)
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