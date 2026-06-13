package io.samples.cola.entity;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */

public class Order {
    private Long id;
    private String code;
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", code='" + code + '\'' + ", state=" + state + '}';
    }
}
