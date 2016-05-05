<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="command.FindUserCommand"%>
<%@page import="command.LoginUserCommand"%>
<%@page import="com.mongodb.BasicDBObject" %>
<%@page import="command.FindUserCommand" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>IMDB Wish List</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

<script type="text/javascript">
	var x;
	$(document).ready(function() {
		
		var sessionData=$('#session').val();
		
		$('#sub').click(function() {
			var requestData = $('#search').val();
			var resultElement = $('#response');
			
			$.ajax({
				url : 'http://www.omdbapi.com/',
				method : 'get',
				data : {
					t : requestData
				},
				success : function(data) {
					//resultElement.html('Title: ' + data.Title +'<br/>'+'Released: ' +data.Released);
					alert(data);
					console.log(data);
					$('#Poster').attr('src', data.Poster);
					$('#Title').html(data.Title);
					$('#Type').html(data.Type);
					$('#imdbRating').html(data.imdbRating);
					$('#Released').html(data.Released);
					$('#imdbId').html(data.imdbID);
					$('#Add').fadeIn(4000);
					$('#table').fadeIn(2000);
					x="id="+data.imdbID+"&releasedDate="+data.Released+"&userId="+sessionData+"&name="+data.Title+"&poster="+data.Poster;
				}
			})
		})
		
		$('#wishSearch').click(function(status, jqxhr) {
		
			$.ajax({
	            url: "http://saasunh.herokuapp.com/rest/user/wish/"+sessionData,
	            type: "GET",
	            dataType: 'json',          
	            success: function (response) {
					console.log(response);
					//alert(response);
					for (var i=0; i < response.length; i++ ) {
						$('#poster2').attr('src', response[i].poster);
						$('#Title2').html(response[i].name);
						$('#imdbId2').html(response[i].imdbId);
						$('#day2').html(response[i].day);
						$('#month2').html(response[i].month);
						$('#year2').html(response[i].year);
						
						$('#table2').fadeIn(2000);
					}
	            },
	            error: function (xhr, status) {
	                alert("error");
	            }
	        });

		});
		
		$('#update').click(function() {
			alert("Update call")
			var first = $('#firstUp').val();
			var last=$('#lastUp').val();
			var email=$('#emailUp').val();
			var password =$('#passwordUp').val();
			var session=$('#session').val();
			
			alert("firstNamee : "+first);
			alert("lastNamee : "+last);
			alert("email : "+email);
			alert("password : "+password);
			alert("session : "+session);
			
			
			var x="firstName="+first+"&lastName="+last+"&email="+email+"&password="+password+"&session="+session;
			alert("URL :"+x);
			
			$.ajax({				
				url: 'http://saasunh.herokuapp.com/rest/user/id?'+x,
				method: 'PUT',
				dataType: 'json',
				success: function (data) {
					//resultElement.html('Title: ' + data.Title +'<br/>'+'Released: ' +data.Released);
					console.log("data----"+data);
					alert("success");
				}, 
				error: function (data){
					console.log("");
					alert("Update has been done, please try to login again.");
				} 
			})
		})
		
		
		$('#addWish').click(function add(){
			
			window.location.href="http://localhost:8080/csci6678/rest/user/wish/add?"+x;
			window.location.reload(true);
		});
	})
</script>
</head>
<body>

	<div class="container">

		<!--  ================ Navigationbar HTML5 Content ================================== -->
		
		

		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="" id="">Wish List</a>
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
		
		
		<!--  ================ /Navigationbar HTML5 Content ================================== -->

		
		<!--  ================ Jumbotron HTML5 Content ================================== -->
		<div class="jumbotron">
			<h1>IMDB Wish List</h1>
			<p>Wishes for Movie/Season/Episodes</p>
		</div>

		<!--  ================ /Jumbotron HTML5 Content ================================== -->

		<!--  ================ Carousel HTML5 Content ================================== -->
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
				<li data-target="#myCarousel" data-slide-to="3"></li>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
				<div class="item active">
					<img class="img-responsive" src="<%=request.getContextPath()%>/view/img/imdbThird.png" alt="Imdb">
				</div>
				<div class="item">
					<img class="img-responsive" src="<%=request.getContextPath()%>/view/img/imdbFirst.png" alt="Imdb">
				</div>
				<div class="item">
					<img class="img-responsive" src="<%=request.getContextPath()%>/view/img/imdbSecond.png" alt="Imdb">
				</div>
				<div class="item">
					<img class="img-responsive" src="<%=request.getContextPath()%>/view/img/imdbForth.png" alt="Imdb">
				</div>
			</div>

			<!-- Left and right controls -->
			<a class="left carousel-control" href="#myCarousel" role="button"
				data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#myCarousel" role="button"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>

		<!--  ================/Carousel HTML5 Content ================================== -->



		<br />
		<div class="panel-group" style="width: 1140px;" align="center">
			<div class="panel panel-default">
				<div class="panel-heading">

					<form class="form-inline" role="form">
						<h4 class="panel-title">
							<div class="form-group">

								<img class="img-responsive" src="img/IMDb-icon-300x167.png"
									style="height: 35px; width: 60px;">

							</div>
							<div class="form-group">
								<div class="dropdown">
									<button class="btn btn-primary dropdown-toggle" type="button"
										data-toggle="dropdown">
										Actions <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li><a href="#">Movies</a></li>
										<li><a href="#">Series</a></li>
										<li><a href="#">Drama</a></li>
									</ul>
								</div>
							</div>
							
							<!-- ----------------Getting data from Session -------------------------------  -->
							
							<input type="text" style="display: none" id="session" value="<%=(String)session.getAttribute("session") %>" >
							
							
							<div class="form-group">
								<input type="search" class="form-control" id="search">
							</div>
							<button type="submit" class="btn btn-default" id="sub">Submit</button>
							<button type="submit" class="btn btn-default" id="wishSearch">Your WishList</button>
							
						</h4>
					</form>


				</div>
				<div id="collapse1" class="panel-collapse collapse">
					<div class="panel-body">Panel Body</div>
					<div class="panel-footer">Panel Footer</div>
				</div>
			</div>
		</div>


		<br/><br/>
		<!--  ================ FORM HTML5 Content ================================== -->
		

			
					<div class="form-group">
						<input type="text" class="form-control" id="firstUp" placeholder="Login name"  required="">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="lastUp" placeholder="Last Name " required="">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="emailUp" placeholder="email" required="">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" id="passwordUp" placeholder="password" required="">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="update">Update</button>
						<button type="reset" class="btn ">Clear</button>
					</div>


		
		
		<!--  ================ /FORM HTML5 Content ================================== -->
		<br /> 
		
			
		<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />


		<!--  ================ PAGER HTML5 Content ================================== -->
		<div>
			<ul class="pager">
				<li class="previous"><a href="#">Previous</a></li>
				<li class="next"><a href="#">Next</a></li>
			</ul>
		</div>
		<!--  ================ /PAGER HTML5 Content ================================== -->


		<!--  ================ COLS HTML5 Content ================================== -->

		<div class="row">
			<div class="col-sm-4">
				<h3>Column 1</h3>
				<p>line 1</p>
				<p>line 2</p>
			</div>
			<div class="col-sm-4">
				<h3>Column 2</h3>
				<p>line 1</p>
				<p>line 2</p>
			</div>
			<div class="col-sm-4">
				<h3>Column 3</h3>
				<p>line 1</p>
				<p>line 2</p>
			</div>
		</div>
		<!--  ================ /COLS HTML5 Content ================================== -->
	</div>
	<script type="text/javascript">
		
	</script>
</body>


</html>