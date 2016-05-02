package command;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.tomcat.util.http.mapper.Mapper;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import model.User;
import mongo.ConnectionProvider;

public class UserInfoCommand {
	
	ObjectMapper mapper = new ObjectMapper();
	
	public User execute(String key, String value) {
		// TODO Auto-generated method stub
		System.out.println("Calling this method");
		ConnectionProvider conn = new ConnectionProvider();
		DBCollection userCollection=null;
		
		try {
			userCollection = conn.getCollection("imdbUser");
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BasicDBObject searchQuery = new BasicDBObject();
		
		System.out.println("Entering into if block");
		if (key.equals("_id")) {
			
			System.out.println("Key is :"+key);
			searchQuery.put(key, new ObjectId(value));
		}else {
			
			System.out.println("Key in else block : "+key);
			System.out.println("value in else block : "+value);
			
			searchQuery.put(key, value);
		}
		
		DBObject user = userCollection.findOne(searchQuery);
		
		User userData=null;
		
		try{
			userData = mapper.readValue(user.toString(), User.class);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return userData;
		
	}
}
