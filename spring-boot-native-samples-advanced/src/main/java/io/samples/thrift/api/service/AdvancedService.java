package io.samples.thrift.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: baoxin.zhao
 * @date: 2024/11/17
 */
@Service
public class AdvancedService {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    public void some() {
        log.info("===========advanced-service===========");
    }

}
