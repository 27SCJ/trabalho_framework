package com.fiap.activemq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
import org.json.JSONArray;
import org.json.JSONObject;

import com.fiap.dao.MensagemDAO;
import com.fiap.model.Mensagem;

/**
 * @author RudraG
 *
 */
public class Consumer {
	private static String url = "tcp://lym-net.com:61616";
	private static String subject = "Fila";
	private final static String USER_AGENT = "Mozilla/5.0";
	private static String urlBook = "http://admin.lym-net.com/docs";
	
	public static List<Mensagem> lerLista() throws SQLException, JMSException,Exception {
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

				//ENVIAR MENSAGEM PELO JSON
				
				String jsonMessage = sendGet(textMessage.getText());
				JSONObject jObject  = new JSONObject(jsonMessage);
				JSONObject data = jObject.getJSONObject("response");
				JSONArray docs = data.getJSONArray("docs");
				String resourcename = docs.getJSONObject(0).getString("resourcename");
				
				try {
					resourcename = urlBook+resourcename.replace("[", "").replace("]", "").replace("\"","").split("docs")[1];
					dao.addMensagem(textMessage.getText(),resourcename);
					
					Mensagem mensagem = new Mensagem();
					mensagem.setMensagemid(id++);
					mensagem.setMensagem(textMessage.getText());
					mensagem.setUrls(url);
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
	
	
	// HTTP GET request
	public static String sendGet(String mensagem) throws Exception {

		
		String url = "http://admin.lym-net.com/busca/select?indent=on&q=*"+mensagem+"*&wt=json";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}
	
	
	
}

