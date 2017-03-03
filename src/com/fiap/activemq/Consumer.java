package com.fiap.activemq;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
import com.fiap.model.Mensagem;

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

				try {
					dao.addMensagem(textMessage.getText());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
			}
			else
				break;
			
		};
		
		connection.close();
	}
	
	
	public static List<Mensagem> lerLista() throws SQLException, JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		MensagemDAO dao = new MensagemDAO();
		int id = 1;

		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(subject);
		MessageConsumer consumer = session.createConsumer(destination);
		
		Message message;
		List<Mensagem> mensagens = new ArrayList<Mensagem>();

		
		while(true) {
			message = consumer.receive(500);

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;

				try {
					dao.addMensagem(textMessage.getText());
					
					Mensagem mensagem = new Mensagem();
					mensagem.setMensagemid(id++);
					mensagem.setMensagem(textMessage.getText());
					mensagens.add(mensagem);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			else
				break;
			
		};
		
		connection.close();
		return mensagens;
	}	
	
	
	
}

