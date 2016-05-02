package test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import command.ListAllUserCommand;
import command.ListAllUserWish;
import command.UserInfoCommand;
import model.User;
import model.WishList;

@Path("user")
public class Sandbox {
	ObjectMapper mapper = new ObjectMapper();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse() throws Exception{
			
		ListAllUserCommand listBooks = new ListAllUserCommand();
			ArrayList<User> list = listBooks.execute();
			String booksString = null;
			try {
				booksString = mapper.writeValueAsString(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Response.status(200).entity(booksString).build();
		}

	@GET
	@Path("wish")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWish() throws Exception{
			
		ListAllUserWish wish = new ListAllUserWish();
			ArrayList<WishList> list = wish.execute();
			String booksString = null;
			try {
				booksString = mapper.writeValueAsString(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Response.status(200).entity(booksString).build();
		}
	
	@GET
	@Path("/{key}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInfo(@PathParam("key") String key, @PathParam("value") String value) throws Exception{
			
		UserInfoCommand userInfo = new UserInfoCommand();
			User user = userInfo.execute(key,value);
			String userData= null;
			
			try {
				userData= mapper.writeValueAsString(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Response.status(200).entity(userData).build();
		}
	
	
}