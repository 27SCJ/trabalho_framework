package com.fiap.controller;

import java.io.IOException;

import javax.jms.JMSException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fiap.activemq.Consumer;
import com.fiap.activemq.Producer;
import com.fiap.dao.MensagemDAO;

public class MensagemController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MensagemDAO dao;

    public MensagemController() {
        super();
        dao = new MensagemDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="index.jsp";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("listMensagem")){
        	try {
				Consumer.receber();
			} catch (JMSException e) {
				e.printStackTrace();
			}
            request.setAttribute("mensagens", dao.getAllMensagens());
            forward = "listMensagem.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    	
    	try {
			Producer.enviar(request.getParameter("txt_mensagem"));
	        RequestDispatcher view = request.getRequestDispatcher("gravado.jsp");
	        view.forward(request, response);			
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	
    }
}