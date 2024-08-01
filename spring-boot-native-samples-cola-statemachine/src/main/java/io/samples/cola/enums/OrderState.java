package io.samples.cola.enums;

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
public enum OrderState {
    INIT(10, "初始化"),
    PAID(20, "已支付"),
    DELIVERED(30, "已发货"),
    REFUNDED(40, "已退货"),
    ;

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
}
