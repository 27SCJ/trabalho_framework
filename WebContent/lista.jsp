<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>Lista</title>
</head>
<body>
<h3>Lista</h3>
<p>Registros</p> 
<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://portalpupit.mysql.dbaas.com.br:3306/portalpupit"
     user="portalpupit"  password="FIAP@!XMKVF7"/>
 
<sql:query dataSource="${snapshot}" var="result">
SELECT * FROM portalpupit.mensagens;
</sql:query>
 
<table border="1" width="360" bordercolor="black" cellspacing="0" cellspading="0" >
<tr>
   <th>Codigo</th>
   <th>Nome</th>
   <th>Local</th>
   <th>Longitude</th>
   <th>Latitude</th>
   <th>Mapa</th>
</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.id}"/></td>
   <td><c:out value="${row.mensagens}"/></td>
   <td><c:out value="${row.urls}" escapeXml="false" /></td>
   <td><c:out value="${row.mensagens}"/></td>
   <td><c:out value="${row.mensagens}"/></td>
   <td>  
   </td>
</tr>
</c:forEach>
</table>

<p><a href="index.jsp">Voltar</a> </p>
  
</body>
</html>