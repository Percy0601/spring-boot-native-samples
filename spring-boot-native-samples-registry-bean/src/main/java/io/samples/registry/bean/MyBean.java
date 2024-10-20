package io.samples.registry.bean;

import java.util.Date;

/**
 * @author: baoxin.zhao
 * @date: 2024/10/20
 */
public class MyBean {
    private Date date;
    public String doSomething () {
        return "来自我的 bean，日期：" + date;
    }
    public void setDate (Date date) {
        this.date = date;
    }
}
