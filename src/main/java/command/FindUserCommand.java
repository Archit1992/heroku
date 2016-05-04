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

public class FindUserCommand {

	public String execute(String login) {
		// TODO Auto-generated method stub
		
		
		ConnectionProvider conn = new ConnectionProvider();
		DBCollection userCollection = null;
		DBCursor cursor=null;
		String fname=null;
		
		try {
			userCollection = conn.getCollection("imdbUser");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		ObjectId id=new ObjectId(login);
		DBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", id);
		
		try{
			cursor= (DBCursor) userCollection.find(searchQuery);
			
		}catch(Exception e){
			System.out.println("ERROR: Getting data from Database.");
			e.printStackTrace();
		}
		try{
			
			while(cursor.hasNext()){
					DBObject obj=cursor.next();
					fname=((BasicBSONObject) obj).getString("firstName");
			}
		}catch(Exception e){
			System.out.println("Error : Getting data from DBCursor."+cursor.toString());
			e.printStackTrace();
		}
		return fname;
		
	}
	public static void main(String[] args) {
		
		FindUserCommand usr = new FindUserCommand();
		String obj= usr.execute("57291cbc6d69cc000376ed57");   // update the parameter value for testing.
		
		if (obj.toString() !=null) {
			System.out.println("SUCCESS::"+obj);
		} else {
			System.out.println("ERROR:Failed to get User"+obj.toString());
		}

	}
}
