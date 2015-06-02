package ru.axmexa.spring.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;


public class MySessionAwareMessageListener implements SessionAwareMessageListener<Message> {
    
    private static final Logger LOG = LoggerFactory.getLogger(MySessionAwareMessageListener.class);

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
        try {
        	String requestText = ((TextMessage)message).getText();
            LOG.info("Received message: {} ", requestText);
            
            // Send a reply message 
            JSONObject answer = new JSONObject();
			try {
				answer = MessageMaker.createAnswerMessage(requestText);
			} catch (StopConsumerException e) {
				System.exit(0);
			}
            
            TextMessage newMessage = session.createTextMessage(answer.toString());
            MessageProducer producer =  session.createProducer(message.getJMSReplyTo());
            
            
            LOG.info("Sending reply message");
            producer.send(newMessage);
            
        } catch (JMSException e) {
        	LOG.error(e.getMessage(), e);
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
	}

}