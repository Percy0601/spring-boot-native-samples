package io.samples.cola.entity;

import lombok.Data;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */

@Data
public class Order {
    private Long id;
    private String code;
    private Integer state;

}
