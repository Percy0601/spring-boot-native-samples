package io.samples.statemachine.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 订单状态（States)
 *
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */
@ToString
@Getter
@AllArgsConstructor
public enum States {
    SI(0, "SI"),
    S1(1, "S1"),
    S2(2, "S2"),
    ;

    private final Integer code;
    private final String name;

    public static States getByCode(Integer code) {
        for (States e: States.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
