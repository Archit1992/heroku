<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<div>
		{
		 "User Status " : ${it}, <br/>
		 "ID stored   " : session, <br/>
		 "IMDB Search"	: <a href="<%=request.getContextPath()%>/view/ImdbSearch.jsp">Login</a>
		}
	</div>
		
		<c:set var="session" scope="session" value="${it}"></c:set>
</body>
</html>