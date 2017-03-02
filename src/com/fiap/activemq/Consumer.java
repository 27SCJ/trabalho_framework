package com.fiap.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.fiap.dao.MensagemDAO;

/**
 * @author RudraG
 *
 */
public class Consumer {
	private static String url = "tcp://lym-net.com:61616";
	private static String subject = "Fila";

	public static void receber() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		MensagemDAO dao = new MensagemDAO();

		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(subject);
		MessageConsumer consumer = session.createConsumer(destination);
		
		Message message;
		
		while(true) {
			message = consumer.receive(500);

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				dao.addMensagem(textMessage.getText());
			}
			else
				break;
			
		};
		
		connection.close();
	}
	
	
	
	
	
	
}

