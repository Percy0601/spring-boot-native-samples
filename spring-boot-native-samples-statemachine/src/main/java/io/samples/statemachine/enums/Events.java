package io.samples.statemachine.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */
@ToString
@Getter
@AllArgsConstructor
public enum Events {
    E1(1, "E1"),
    E2(2, "E2"),
    ;

    private final Integer code;
    private final String name;

    public static Events getByCode(Integer code) {
        for (Events e: Events.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
