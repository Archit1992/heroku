package test;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import command.ListAllUserCommand;
import command.ListAllUserWish;
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

		ListAllUserCommand listBooks = new ListAllUserCommand();
		ArrayList<User> list = listBooks.execute();
		String booksString = null;
		try {
			booksString = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(booksString).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	// getting data of all wishes... 
	@GET
	@Path("wish")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWish() throws Exception {

		ListAllUserWish wish = new ListAllUserWish();
		ArrayList<WishList> list = wish.execute();
		String booksString = null;
		try {
			booksString = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(booksString).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	
	
	// getting a data for specific user... 
	@GET
	@Path("/{key}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInfo(@QueryParam("key") String key, @QueryParam("value") String value) throws Exception {

		UserInfoCommand userInfo = new UserInfoCommand();
		User user = userInfo.execute(key, value);
		String userData = null;

		try {
			userData = mapper.writeValueAsString(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(userData).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}

	
	// Registration purposes... 
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response post(@FormParam("firstName") String login, @FormParam("lastName") String lastname,
			@FormParam("email") String email, @FormParam("password") String password) {
		User s = new User();
		s.setFirstName(login);
		s.setLastName(lastname);
		s.setEmail(email);
		s.setPassword(password);
		
		CreateUserCommand cmd = new CreateUserCommand();
		if(cmd.execute(s)){
			return Response.ok(new Viewable("/view/success.jsp")).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}else{
			return Response.ok(new Viewable("/view/Error.jsp", "Please Register again...")).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}
		
	}
	
	
	// Update User Account data... 
	@PUT
	@Path("/id")
	public Response deleteBook(@QueryParam("firstName") String login, @QueryParam("lastName") String lastname,
			@QueryParam("email") String email, @QueryParam("password") String password, @QueryParam("session") String id) {
		
		UpdateUserCommand update = new UpdateUserCommand();

		User s = new User();
		s.setFirstName(login);
		s.setLastName(lastname);
		s.setEmail(email);
		s.setPassword(password);
		
		if(update.execute(id,s)){
			return Response.status(200).build();
		}else{
			return Response.status(400).build();
		}
		
	}
	
	@GET
	@Path("/login")
	public Response doLogin(){
		
		return Response.ok(new Viewable("/view/Register.jsp")).header("Access-Control-Allow-Origin", "*")
	.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	
	
	
	// ============================== EXTRA Services, Working on it =========================== 
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
}