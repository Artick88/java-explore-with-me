package ru.practicum.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StateUserActionEvent {
    SEND_TO_REVIEW(StateEvent.PENDING), CANCEL_REVIEW(StateEvent.CANCELED);

    private final StateEvent stateEvent;
}
