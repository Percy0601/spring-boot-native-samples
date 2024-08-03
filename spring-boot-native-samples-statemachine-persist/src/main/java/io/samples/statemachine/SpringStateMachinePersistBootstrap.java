package io.samples.statemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.config.EnableStateMachine;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@EnableStateMachine
@SpringBootApplication
public class SpringStateMachinePersistBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringStateMachinePersistBootstrap.class, args);
    }

}
