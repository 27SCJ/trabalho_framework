<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Lendo e gravando em banco</title>
</head>
<body>

	<c:choose>
	    <c:when test="${not empty mensagens}">
	        Novas mensagens lidas e gravadas em banco:
	                  
			    <table border=1>
			        <thead>
			            <tr>
			                <th>#</th>
			                <th>Mensagem</th>
			            </tr>
			        </thead>
			        <tbody>
			            <c:forEach items="${mensagens}" var="mensagem">
			                <tr>
			                    <td><c:out value="${mensagem.mensagemid}" /></td>
			                    <td><c:out value="${mensagem.mensagem}" /></td>
			                </tr>
			            </c:forEach>
			        </tbody>
			    </table>
	    </c:when>
	    <c:otherwise>
	        Sem novas mensagens na fila!!!
	    </c:otherwise>
	</c:choose> 
	

    
    <p><a href="index.jsp">Voltar</a> </p>
</body>
</html>