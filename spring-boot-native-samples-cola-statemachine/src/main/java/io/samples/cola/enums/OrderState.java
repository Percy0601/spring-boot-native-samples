package io.samples.cola.enums;

/**
 * 订单状态（States)
 *
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */

public enum OrderState {
    INIT(10, "初始化"),
    PAID(20, "已支付"),
    DELIVERED(30, "已发货"),
    REFUNDED(40, "已退货"),
    ;

    OrderState(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private final Integer code;
    private final String name;

    public static OrderState getByCode(Integer code) {
        for (OrderState e: OrderState.values()) {
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
        return "OrderState{" + "code=" + code + ", name='" + name + '\'' + '}';
    }

    public String getName() {
        return name;
    }
}
