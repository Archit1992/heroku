<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="command.FindUserCommand"%>
<%@page import="command.LoginUserCommand"%>
<%@page import="com.mongodb.BasicDBObject" %>
<%@page import="command.FindUserCommand" %>

<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Wish List</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					
					
					<% 
						String id=(String)session.getAttribute("session");
					%>
										
					<li><a href="<%=request.getContextPath()%>/rest/user/details?id=<%=id%>" >Account</a></li>
					
					 <% 
						
						FindUserCommand command=new FindUserCommand();
						String fName=command.execute(id);
					 %>
					 <li style="padding-left: 660px;"><a href="#">
						<%=fName %> 
					</a>
					</li>
					<li style="padding-left: 10px;"><a href="<%=request.getContextPath()%>/view/Logout.jsp">Logout</a></li>
				</ul>
				
				<div style="display: none;">
						<input type="text" id="userName" value="<%=fName%>"></input>
				</div>
			</div>
		</nav>