package com.fiap.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
	private static String url = "tcp://lym-net.com:61616";
	private static String subject = "Fila"; 
	
	public static void enviar(String mensagem) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination destination = session.createQueue(subject);
		MessageProducer producer = session.createProducer(destination);
		TextMessage message = session.createTextMessage(mensagem);
		
		producer.send(message);
		connection.close();		
	}
	
}