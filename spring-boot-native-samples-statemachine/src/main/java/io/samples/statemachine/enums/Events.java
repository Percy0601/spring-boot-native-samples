package io.samples.statemachine.enums;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */

public enum Events {
    E1(1, "E1"),
    E2(2, "E2"),
    ;

    Events(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

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

    public Integer getCode() {
        return code;
    }


    @Override
    public String toString() {
        return "Events{" + "code=" + code + ", name='" + name + '\'' + '}';
    }

    public String getName() {
        return name;
    }
}
