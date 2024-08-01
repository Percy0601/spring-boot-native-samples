package io.samples.cola.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;

import io.samples.cola.entity.Order;
import io.samples.cola.enums.OrderEvent;
import io.samples.cola.enums.OrderState;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import static io.samples.cola.enums.Constant.MACHINE_ID;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {
    StateMachineBuilder<OrderState, OrderEvent, Order> builder;
    StateMachine<OrderState, OrderEvent, Order> stateMachine;
    @PostConstruct
    private void init() {
        builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(OrderState.INIT)
                .to(OrderState.PAID)
                .on(OrderEvent.PAY_SUCCESS)
                .when(checkCondition())
                .perform(doAction());
        stateMachine = builder.build(MACHINE_ID);

    }

    @GetMapping("/hello")
    public String hello(String name) {
        if(!StringUtils.hasText(name)) {
            name = "Percy";
        }
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/send-event")
    public String sendEvent() {
        Order order = new Order();
        order.setCode("DD001");
        order.setState(OrderState.INIT.getCode());
        OrderState target = stateMachine.fireEvent(OrderState.INIT, OrderEvent.PAY_SUCCESS, order);
        return target.toString();
    }

    private Action<OrderState, OrderEvent, Order> doAction() {
        return (from, to, event, ctx) -> {
            log.info("state from:{} -> to:{}, event:{}, pay Success, ctx:{}", from, to, event, ctx);
        };
    }


    private Condition<Order> checkCondition() {
        return order -> {
            log.info("Check condition: {}", order);
            if(order.getState().equals(OrderState.INIT.getCode())) {
                return true;
            }
            return false;
        };
    }
}
