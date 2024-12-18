package io.samples.statemachine.config;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;

import io.samples.statemachine.enums.Events;
import io.samples.statemachine.enums.States;
import io.samples.statemachine.repo.Persist;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/2
 */
@Slf4j
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {


    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                log.info("State change to: {}", to.getId());
            }
        };
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                .source(States.SI)
                .target(States.S1)
                .event(Events.E1)

                .and().withExternal()
                .source(States.S1)
                .target(States.S2)
                .event(Events.E2);
    }
    //end::snippetA[]

    //tag::snippetB[]
    @Configuration
    static class PersistHandlerConfig {

        @Autowired
        private StateMachine<String, String> stateMachine;

        @Bean
        public Persist persist() {
            return new Persist(persistStateMachineHandler());
        }

        @Bean
        public PersistStateMachineHandler persistStateMachineHandler() {
            return new PersistStateMachineHandler(stateMachine);
        }

    }

}
