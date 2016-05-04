package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

import com.fasterxml.jackson.databind.ObjectMapper;

import command.CreateUserCommand;
import command.CreateWishCommand;
import command.ListAllUserCommand;
import command.ListAllUserWish;
import command.LoginUserCommand;
import command.UpdateUserCommand;
import command.UserInfoCommand;
import model.User;
import model.WishList;

@Path("user")
public class Sandbox {
	ObjectMapper mapper = new ObjectMapper();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse() throws Exception {

		ListAllUserCommand listUsers = new ListAllUserCommand();
		ArrayList<User> list = listUsers.execute();
		String userString = null;
		try {
			userString = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(userString != null){
			// if users found...
			return Response.status(200).entity(userString).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}else{
			// if users will not be found...
			return Response.ok(new Viewable("/view/Error404.jsp", "Not a single user exist!..."))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		
		}
	}

	// getting data of all wishes...
	
	@GET
	@Path("wish")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWish() throws Exception {

		ListAllUserWish wish = new ListAllUserWish();
		ArrayList<WishList> list = wish.execute();
		String wishString = null;
		try {
			wishString = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return all user's wish data
		if(wishString != null){
			// if wishes will be there...
			return Response.status(200).entity(wishString).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}else{
			// if wishes will not be there...
			return Response.ok(new Viewable("/view/Error404.jsp", "Not a single wish exist!..."))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		
		}
		
	}

	// getting a data for specific user...
	// link : http://saasunh.herokuapp.com/rest/user/{key}/{value}
	@GET
	@Path("/{key}/{value}")
	@Produces(MediaType.APPLICATION_JSON)	// produces JSON data as response.
	public Response getUserInfo(@PathParam("key") String key, @PathParam("value") String value) throws Exception {

		UserInfoCommand userInfo = new UserInfoCommand();
		User user = userInfo.execute(key, value);	
		String userData = null;

		try {
			userData = mapper.writeValueAsString(user); // mapped user object data as a String to userData
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(userData !=null){
			// if user found!
			return Response.status(200).entity(userData).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}else{
			// if user not found!
			return Response.ok(new Viewable("/view/Error404.jsp", "User is not exist..."))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}
		
	}

	// Registration purposes...
	// link : http://saasunh.herokuapp.com/rest/user/create/{FormParam}
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response post(@FormParam("firstName") String login, @FormParam("lastName") String lastname,
			@FormParam("email") String email, @FormParam("password") String password) {
		
		User s = new User();   // setting user details into model.
		s.setFirstName(login);
		s.setLastName(lastname);
		s.setEmail(email);
		s.setPassword(password);
		
		CreateUserCommand cmd = new CreateUserCommand();
		if (cmd.execute(s)) {
			//// if user will be created...
			return Response.ok(new Viewable("/view/userCreated.jsp", " User successfully created!"))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		} else {
			return Response.ok(new Viewable("/view/Error404.jsp", "Please Register again..."))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}

	}

	// During Login time, this web-service will be called...
	// link : http://saasunh.herokuapp.com/rest/user/log/{FormParam}
	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response post(@FormParam("firstName") String login, @FormParam("password") String password) {

		User s = new User();
		s.setFirstName(login);
		s.setPassword(password);

		LoginUserCommand user = new LoginUserCommand();
		String userId = user.execute(s); // this method will give you specific user Id, it it exist!

		if (userId != null) {
			// if userId will get...
			return Response.ok(new Viewable("/view/success.jsp", userId)).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		} else {
			// if userId will not get...
			return Response.ok(new Viewable("/view/Error.jsp", "Please Register first..."))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}

	}

	// Update User Account data...
	// link : http://saasunh.herokuapp.com/rest/user/id/{QueryParam}
	@PUT
	@Path("/id")
	@Produces(MediaType.APPLICATION_JSON) // will produce JSON data as response.
	public Response deleteBook(@QueryParam("firstName") String login, @QueryParam("lastName") String lastname,
			@QueryParam("email") String email, @QueryParam("password") String password,
			@QueryParam("session") String id) {

		UpdateUserCommand update = new UpdateUserCommand();

		User s = new User();
		s.setFirstName(login);
		s.setLastName(lastname);
		s.setEmail(email);
		s.setPassword(password);

		if (update.execute(id, s)) {

			// if data will be changed...
			return Response.ok(new Viewable("/view/success.jsp")).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		} else {
			// if data will not be changed/ Error.jsp will be displayed...
			return Response.ok(new Viewable("/view/Error404.jsp", "Please go back and try again..."))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}

	}

	// During login time...
	// link : http://saasunh.herokuapp.com/rest/user/login
	@GET
	@Path("/login")
	public Response doLogin() {

		return Response.ok(new Viewable("/view/Register.jsp")).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	/*@DELETE
	@Path("/{id}")
	public Response doDelete() {
		
		
		return Response.ok(new Viewable("/view/Register.jsp")).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}*/
	
	
	@SuppressWarnings("deprecation")
	@GET
	@Path("add")
	public Response addWish(@QueryParam("id") String imdbId, @QueryParam("releasedDate") String releaseDate,
			@QueryParam("userId") String objId, @QueryParam("name") String name,@QueryParam("poster") String poster) {

		System.out.println("IMDB ID  is : " + imdbId);
		System.out.println("IMDB Date is : " + releaseDate);

		// Error: MM/dd/yyyy
		SimpleDateFormat x = new SimpleDateFormat("dd MMMMM yyyy", Locale.ENGLISH);
		Date d1 = null;
		try {
			d1 = x.parse(releaseDate);
		} catch (ParseException e) {
			Response.status(400).entity("Invalid date format.").build();
		}
		System.out.println("Day :" + d1.getDay() + " Month: " + d1.getMonth() + " Year: " + d1.getYear());

		WishList wish = new WishList();

		wish.setDate(d1.getDay(), d1.getMonth(), d1.getYear());
		wish.setImdbId(imdbId);
		wish.setUserId(objId);
		wish.setName(name);
		wish.setPoster(poster);
		
		CreateWishCommand userWish = new CreateWishCommand();
		String imdb_Id = userWish.execute(wish);
		System.out.println("User wish IMDB id ="+imdb_Id);
		
		if (imdb_Id == null) {
			return Response.ok(new Viewable("/view/Error404.jsp", " Bad request")).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();

		} else {
			return Response.ok(new Viewable("/view/AddWish.jsp", imdb_Id)).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}

	}
	
	

	// ============================== EXTRA Services, Working on it
	// ===========================
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response createBook(String userStr) {

		System.out.println("calling POST services");
		try {
			CreateUserCommand create = new CreateUserCommand();
			System.out.println("User String:" + userStr);
			User user = mapper.readValue(userStr, User.class);

			boolean success = create.execute(user);
			System.out.println("success val : " + success);
			if (success) {
				return Response.status(201).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
			} else
				return Response.status(400).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}
	}
	
	@GET
	@Path("wishlist")
	public Response getImdbSearch(){
		
		return Response.ok(new Viewable("/view/ImdbSearch.jsp")).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		
		
	}
}