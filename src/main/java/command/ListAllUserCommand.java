package command;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import model.User;
import mongo.ConnectionProvider;

public class ListAllUserCommand {
	ObjectMapper mapper = new ObjectMapper();

	public ArrayList<User> execute() throws UnknownHostException {
		
		ConnectionProvider conn = new ConnectionProvider();
		DBCollection booksCollection = conn.getCollection("imdbUser");

		DBCursor cursor = booksCollection.find();

		ArrayList<User> users = new ArrayList<User>();
		// GetBookCommand getBook = new GetBookCommand();
		BasicDBObject searchQuery = new BasicDBObject();
		User b =null;
		try {
			while (cursor.hasNext()) {
				b= mapper.readValue(cursor.next().toString(), User.class);
				users.add(b);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			cursor.close();
		}
		return users;
	}
}
