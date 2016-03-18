package com.adecco.emea.clients;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.tibco.tibjms.TibjmsConnectionFactory;

public class JMSClient {

	private static JMSClient client;
	
	private Connection queueConnection;
	private TibjmsConnectionFactory queueFactory;
	private String connectionString, user, password;
	
	public JMSClient(String connectionString, String user, String password) {
		this.connectionString=connectionString;
		this.user=user;
		this.password=password;
	}

	public void open() throws JMSException {
		queueFactory = new TibjmsConnectionFactory(connectionString);
		queueConnection  = queueFactory.createConnection(user,password);
	}

	public String sendMessageReplay(String topicNameSend,String queueReponse, String message) throws JMSException {
		
		UUID uid= UUID.randomUUID();
		Session queueSession = queueConnection.createSession();
		
		Destination destination = queueSession.createQueue(topicNameSend);
		Destination replyTo= queueSession.createQueue(queueReponse);
		
		MessageProducer messageProducer = queueSession.createProducer(destination);
		queueConnection.start();
		
		TextMessage msg;
		
		msg = queueSession.createTextMessage();
		msg.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
		msg.setJMSExpiration(100000);
		msg.setJMSCorrelationID(uid.toString());
		msg.setJMSReplyTo(replyTo);
		msg.setText(message);
		System.out.println(uid);
		String messgeSelector = "JMSCorrelationID = '" + uid + "'";
		
		
		
		MessageConsumer replyConsumer = queueSession.createConsumer(replyTo,messgeSelector);
		messageProducer.send(msg, javax.jms.DeliveryMode.NON_PERSISTENT,   javax.jms.Message.DEFAULT_PRIORITY, 5000);
		
		Message replayMessage = replyConsumer.receive(20000);
		TextMessage textMessage = (TextMessage) replayMessage;

		String replayText="";
		if(textMessage!=null)
		replayText = textMessage.getText();
		
		
		queueSession.close();
		return  replayText;

		
	}
	
	public void close() throws JMSException{
		queueConnection.close();
	}

	public static JMSClient getClient(String connectionString, String user,
			String password) throws JMSException {
		if(client==null){
	    	client=new JMSClient(connectionString, user, password);
	    	client.open();
		}
		return client;
	}
	

	
	
}