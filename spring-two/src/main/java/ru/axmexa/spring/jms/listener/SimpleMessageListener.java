package ru.axmexa.spring.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMessageListener implements MessageListener {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleMessageListener.class);

    public void onMessage(Message message) {
        try {
            LOG.info("Received message: {}", ((TextMessage)message).getText());
//            System.out.println(String.format("Received message: {%s}", ((TextMessage)message).getText()));
        } catch (JMSException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}