<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Mostrando mensagens</title>
</head>
<body>
	<h2>Listar mensagens</h2>

    <table border=1>
        <thead>
            <tr>
                <th width=100>Codigo</th>
                <th width=100>Mensagem</th>
                <th width=300>Links</th>
                <th width=300>URLs</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${mensagens}" var="mensagem">
                <tr>
                    <td><c:out value="${mensagem.mensagemid}" /></td>
                    <td><c:out value="${mensagem.mensagem}" /></td>
                    <td> <a target="_blank" href="${mensagem.urls}" ></a>Paginas encontradas</td>
                    <td><c:out value="${mensagem.urls}" escapeXml="false" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <p><a href="index.jsp">Voltar</a> </p>
</body>
</html>