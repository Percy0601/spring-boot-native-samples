package io.samples.mybatis.controller;

import java.util.UUID;

import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.mybatis.MessageService;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@RestController
@RequestMapping("/rocket-mq")
public class RocketMQController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageService messageService;

    @GetMapping("/send")
    public String send(String msg) {
        if(!StringUtils.hasText(msg)) {
            msg = UUID.randomUUID().toString();
        }

        SendReceipt sendReceipt = messageService.send(msg);
        return sendReceipt.getMessageId().toString();
    }



}
