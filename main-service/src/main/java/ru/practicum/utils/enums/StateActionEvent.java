package ru.practicum.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StateActionEvent {
    PUBLISH_EVENT(StateEvent.PUBLISHED), REJECT_EVENT(StateEvent.CANCELED);

    private final StateEvent stateEvent;
}
