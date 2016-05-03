package command;

import java.net.UnknownHostException;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.User;
import mongo.ClientProvider;
import mongo.ConnectionProvider;

public class CreateUserCommand {
	
	ObjectMapper mapper = new ObjectMapper();
	
	public boolean execute(User user) {
		// TODO Auto-generated method stub
		
		
		ClientProvider cli=new ClientProvider();
		MongoClient mongoclient=cli.getClient();
		
		MongoDatabase db = mongoclient.getDatabase("saasunh");
		MongoCollection<Document> userColl = db.getCollection("imdbUser");
		
		try {
			Document dbObject = new Document(Document.parse(mapper.writeValueAsString(user)));
			System.out.println("DB Object is :"+dbObject.toString());
			userColl.insertOne(dbObject);
			return true;
		} catch (Exception e) {
			System.out.println("ERROR during mapping book to Mongo Object");
			return false;
		}
		finally{
			mongoclient.close();
		}
		
	}
}
