package command;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import model.User;
import mongo.ConnectionProvider;

public class UpdateUserCommand {

	public boolean execute(String id, User s) {
		System.out.println("Calling this method");
		ConnectionProvider conn = new ConnectionProvider();
		DBCollection userCollection=null;
		
		try {
			userCollection = conn.getCollection("imdbUser");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BasicDBObject fromQuery = new BasicDBObject();
		BasicDBObject toQuery = new BasicDBObject();
		
		fromQuery.put("_id", new ObjectId(id));
		
		toQuery.put("firstName",s.getFirstName());
		toQuery.put("lastName", s.getLastName());
		toQuery.put("email", s.getEmail());
		toQuery.put("password",s.getPassword());
		
		try{
			userCollection.update(fromQuery,new BasicDBObject("$set",toQuery));
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
		
	}
}
