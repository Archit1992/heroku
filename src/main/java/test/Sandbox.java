package test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import command.ListAllUserCommand;
import model.User;

@Path("users")
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

	}