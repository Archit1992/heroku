<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IMDB Wish List</title>
</head>
<body>
	<%
			String ObjId=(String)session.getAttribute("sessionlog");
	%>
	
	<div style="font-family: arial">
		{ " success 200" : ${it}, <br/> 
		  " click here" : <a href="<%=request.getContextPath()%>/view/WishList.jsp?id=<%=ObjId%>">Your
			Wish List</a><br/> 
			"check :" <a href="https://saasunh.herokuapp.com/rest/user/wish">here</a>
		}
	</div>
	
		
</body>
</html>