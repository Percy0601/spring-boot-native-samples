package io.samples.statemachine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.statemachine.enums.Events;
import io.samples.statemachine.enums.States;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@RestController
@RequestMapping("/web")
public class WebController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

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
