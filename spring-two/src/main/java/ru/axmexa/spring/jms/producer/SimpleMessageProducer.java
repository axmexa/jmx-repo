package ru.axmexa.spring.jms.producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SimpleMessageProducer {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleMessageProducer.class);
	protected int numberOfMessages = 3;
	protected JmsTemplate jmsTemplate;
	protected Queue replyToDestination;
	private static final String SCRIPT_NAME = "0";
	private static final String SCRIPT_TEXT = "1";
	private static final String STOP_MESSAGE = "2";
	

	public void setNumberOfMessages(int numberOfMessages) {
		this.numberOfMessages = numberOfMessages;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Queue getReplyToDestination() {
		return replyToDestination;
	}

	public void setReplyToDestination(Queue replyToDestination) {
		this.replyToDestination = replyToDestination;
	}

	public void sendMessages() throws JMSException, Exception {

		final JSONObject requestA = new JSONObject();
		requestA.put("type", SCRIPT_NAME);
		requestA.put("value", "perla");
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(requestA.toString());
				message.setJMSReplyTo(replyToDestination);
				return message;
			}
		});
		
		Thread.sleep(2000);
		
		final JSONObject requestB = new JSONObject();
		requestB.put("type", SCRIPT_TEXT);
		requestB.put("value", "for (my $i=0; $i <= 1024; $i++) {print \"a\";}");
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(requestB.toString());
				message.setJMSReplyTo(replyToDestination);
				return message;
			}
		});
		
		Thread.sleep(5000);

		final JSONObject requestSTOP = new JSONObject();
		requestSTOP.put("type", STOP_MESSAGE);
		requestSTOP.put("value", "BYE");
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(requestSTOP.toString());
				message.setJMSReplyTo(replyToDestination);
				return message;
			}
		});

	}
}