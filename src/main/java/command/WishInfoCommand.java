package command;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.User;
import model.WishList;
import mongo.ClientProvider;
import mongo.ConnectionProvider;

public class WishInfoCommand {

	ObjectMapper mapper = new ObjectMapper();

	public ArrayList<WishList> execute(String value) {
		// TODO Auto-generated method stub
		ClientProvider cli=new ClientProvider();
		MongoClient mongoclient=cli.getClient();
		
		MongoDatabase db = mongoclient.getDatabase("saasunh");
		MongoCollection<Document> userColl = db.getCollection("imdbUserWishList");
		
		BasicDBObject searchQuery = new BasicDBObject();
		System.out.println("user collection name is "+db.getName());
				
		searchQuery.put("userId",value);
		System.out.println("Search query is :"+searchQuery.toString());
		ArrayList<WishList> wishList = new ArrayList<WishList>();		
		
		try {
			FindIterable<Document> cursor = userColl.find(searchQuery);
			for (Document c : cursor) {
				System.out.println("Document :::::::::: "+c);
				WishList b = mapper.convertValue(c, WishList.class);

				wishList.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		return wishList;
	}

	public static void main(String[] args) throws UnknownHostException {

		WishInfoCommand usr = new WishInfoCommand();
		String str= "5706f1421b59162524d9e1c5";
		System.out.println("value in else block : " +str);
		
		ArrayList<WishList> usr1= usr.execute(str);
		if (usr1.toString() != null) {
			System.out.println("SUCCESS::" + usr1);
		} else {
			System.out.println("ERROR:Failed to get User" + usr1.toString());
		}

	}
}
