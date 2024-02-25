package ru.practicum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.model.entity.Event;

@Getter
@Setter
@NoArgsConstructor
public class EventStat {

    private Event event;

    private Integer confirmedRequests;

}
