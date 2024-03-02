package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.dto.event.EventRequestStatusUpdateRequestDto;
import ru.practicum.dto.event.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.request.RequestDto;
import ru.practicum.model.UpdateRequest;
import ru.practicum.model.entity.Request;
import ru.practicum.utils.enums.StatusRequest;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {
    RequestDto toRequestDto(Request request);

    List<RequestDto> toRequestDto(List<Request> request);

    UpdateRequest toUpdateRequest(EventRequestStatusUpdateRequestDto requestDto);

    default EventRequestStatusUpdateResultDto toUpdateRequestResultDtoCustom(List<Request> requests) {

        List<Request> confirmedRequests = requests.stream().filter(request ->
                        request.getStatus().equals(StatusRequest.CONFIRMED))
                .collect(Collectors.toList());

        List<Request> rejectedRequests = requests.stream().filter(request ->
                request.getStatus().equals(StatusRequest.REJECTED)).collect(Collectors.toList());

        return EventRequestStatusUpdateResultDto.builder()
                .confirmedRequests(toRequestDto(confirmedRequests))
                .rejectedRequests(toRequestDto(rejectedRequests))
                .build();
    }
}
