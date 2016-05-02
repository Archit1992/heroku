package command;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import model.User;
import model.WishList;
import mongo.ConnectionProvider;

public class ListAllUserWish {
	
	ObjectMapper mapper = new ObjectMapper();
	
	public ArrayList<WishList> execute() {
		// TODO Auto-generated method stub
		ConnectionProvider conn = new ConnectionProvider();
		DBCollection WishCollection = null;
		
		try {
			WishCollection = conn.getCollection("imdbUserWishList");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		DBCursor cursor = WishCollection.find();

		ArrayList<WishList> wishList = new ArrayList<WishList>();
		// GetBookCommand getBook = new GetBookCommand();
		
		System.out.println("------------"+cursor.toString());
		WishList wish =null;
		try {
			while (cursor.hasNext()) {
				wish= mapper.readValue(cursor.next().toString(), WishList.class);
				wishList.add(wish);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			cursor.close();
		}
		return wishList;
	}
	}


