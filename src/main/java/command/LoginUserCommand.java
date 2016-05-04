package command;

import java.net.UnknownHostException;

import org.bson.BasicBSONObject;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import model.User;
import mongo.ConnectionProvider;

public class LoginUserCommand {

	public String execute(User user) {
		// TODO Auto-generated method stub
		
		
		ConnectionProvider conn = new ConnectionProvider();
		DBCollection userCollection = null;
		DBCursor cursor=null;
		String id=null;
		
		try {
			userCollection = conn.getCollection("imdbUser");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("firstName", user.getFirstName());
		searchQuery.put("password", user.getPassword());
		
		
		try{
			cursor= userCollection.find(searchQuery);
			
		}catch(Exception e){
			System.out.println("ERROR: Getting data from Database.");
			e.printStackTrace();
		}
		try{
			
			while(cursor.hasNext()){
					DBObject obj=cursor.next();
					id=String.valueOf(obj.get("_id"));
			}
		}catch(Exception e){
			System.out.println("Error : Getting data from DBCursor."+cursor.toString());
			e.printStackTrace();
		}
		return id;
		
	}
	public static void main(String[] args) {
		
		LoginUserCommand usr = new LoginUserCommand();
		
		User usr1 = new User();
		usr1.setFirstName("Archit");
		usr1.setPassword("hello");
		
		String obj= usr.execute(usr1);   // update the parameter value for testing.
		
		if (obj.toString() !=null) {
			System.out.println("SUCCESS::"+obj);
		} else {
			System.out.println("ERROR:Failed to get User"+obj.toString());
		}

	}
}
