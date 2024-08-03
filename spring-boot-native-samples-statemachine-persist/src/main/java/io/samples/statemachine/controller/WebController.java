package io.samples.statemachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.statemachine.enums.Events;
import io.samples.statemachine.enums.States;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {

    @Autowired
    StateMachine<States, Events> stateMachine;

    @GetMapping("/hello")
    public String hello(String name) {
        if(!StringUtils.hasText(name)) {
            name = "Percy";
        }
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/send-event")
    public void sendEvent(Integer ev){
        log.info("send-event: {}", ev);
        stateMachine.sendEvent(Events.getByCode(ev));
        stateMachine.sendEvent(Events.getByCode(ev));
    }
}
