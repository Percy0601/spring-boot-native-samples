package io.samples.statemachine.entity;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/1
 */
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", code='" + code + '\'' + ", state='" + state + '\'' + '}';
    }
}
