package io.samples.statemachine.entity;

import lombok.Data;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */

@Data
public class Order {
    private Integer id;
    private String code;
    private String state;

    public Order() {

    }

    public Order(Integer id, String state) {
        this.id = id;
        this.state = state;
    }
}
