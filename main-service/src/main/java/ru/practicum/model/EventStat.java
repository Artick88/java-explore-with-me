package ru.practicum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.model.entity.Event;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class EventStat {
    private Event event;
    private Integer confirmedRequests;
}
