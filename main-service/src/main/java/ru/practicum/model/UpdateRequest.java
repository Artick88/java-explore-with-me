package ru.practicum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.utils.enums.StatusRequest;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UpdateRequest {
    private List<Long> requestIds;
    private StatusRequest status;
}
