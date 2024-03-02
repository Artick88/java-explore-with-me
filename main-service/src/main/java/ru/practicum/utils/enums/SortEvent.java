package ru.practicum.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortEvent {
    EVENT_DATE("eventDate"), VIEWS("countViews");

    private final String nameFiled;
}
