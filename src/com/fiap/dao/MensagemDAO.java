package com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fiap.model.Mensagem;
import com.fiap.util.ConnectionFactory;

public class MensagemDAO {

	public void addMensagem(String mensagem,String url) throws SQLException {
		Connection con = new ConnectionFactory().getConnection();
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement("insert into mensagens(mensagem,urls) values (?,?)");
			preparedStatement.setString(1, mensagem);
			preparedStatement.setString(2, url);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	public List<Mensagem> getAllMensagens() throws SQLException {
		Connection con = new ConnectionFactory().getConnection();

		List<Mensagem> mensagens = new ArrayList<Mensagem>();
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select * from mensagens");
			while (rs.next()) {
				Mensagem mensagem = new Mensagem();
				mensagem.setMensagemid(rs.getInt("id"));
				mensagem.setMensagem(rs.getString("mensagem"));
				mensagem.setUrls(rs.getString("urls"));
				mensagens.add(mensagem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return mensagens;
	}

}
