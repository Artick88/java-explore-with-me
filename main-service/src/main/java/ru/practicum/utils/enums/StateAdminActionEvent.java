package ru.practicum.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StateAdminActionEvent {
    PUBLISH_EVENT(StateEvent.PUBLISHED), REJECT_EVENT(StateEvent.CANCELED);

    private final StateEvent stateEvent;
}
