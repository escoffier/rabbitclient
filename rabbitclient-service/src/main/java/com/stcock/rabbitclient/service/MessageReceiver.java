package com.stcock.rabbitclient.service;

import com.stcock.rabbitclient.service.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    public void receiveMessage(String message) {
        System.out.println("MessageReceiver Received <" + message + ">  -- " + Thread.currentThread().getName());
    }

    public void receiveMessage(Quote message) {
        LOGGER.info("MessageReceiver Received <" + message + ">  -- " + Thread.currentThread().getName());
    }
}
