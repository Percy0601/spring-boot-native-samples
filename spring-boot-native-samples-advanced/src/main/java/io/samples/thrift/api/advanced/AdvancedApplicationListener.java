package io.samples.thrift.api.advanced;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author: baoxin.zhao
 * @date: 2024/11/17
 */

public class AdvancedApplicationListener implements ApplicationListener<ApplicationStartedEvent> {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("##########:{}", event.getSource());
    }
}
