package ru.axmexa.spring.jms;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerApp {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerApp.class);
    
    public static void main(String[] args) throws JMSException {
    	new ClassPathXmlApplicationContext("/META-INF/spring/consumer-jms-context.xml", ConsumerApp.class);
    	
    }
    
}