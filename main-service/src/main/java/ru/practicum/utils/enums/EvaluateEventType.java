package ru.practicum.utils.enums;

import ru.practicum.exception.NoSuchEnumException;

public enum EvaluateEventType {

    LIKE, DISLIKE;

    public static EvaluateEventType fromStringIgnoreCase(String data) {
        if (data != null) {
            for (EvaluateEventType sortType : EvaluateEventType.values()) {
                if (data.equalsIgnoreCase(sortType.toString())) {
                    return sortType;
                }
            }
        }
        throw new NoSuchEnumException(String.format("Unknown state: %s", data), ReasonExceptionEnum.BAD_PARAMETER.getReason());
    }

}