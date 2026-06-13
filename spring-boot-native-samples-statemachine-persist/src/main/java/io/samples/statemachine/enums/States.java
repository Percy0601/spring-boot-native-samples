package io.samples.statemachine.enums;

/**
 * 订单状态（States)
 *
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */

public enum States {
    SI(0, "SI"),
    S1(1, "S1"),
    S2(2, "S2"),
    ;

    States(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

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

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "States{" + "code=" + code + ", name='" + name + '\'' + '}';
    }
}
