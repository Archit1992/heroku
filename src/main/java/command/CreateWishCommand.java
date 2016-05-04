package command;

import java.net.UnknownHostException;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import model.WishList;
import mongo.ConnectionProvider;

public class CreateWishCommand {

	public String execute(WishList wish)  {
		// TODO Auto-generated method stub

		ObjectMapper mapper = new ObjectMapper();

		ConnectionProvider userConn = new ConnectionProvider();
		DBCollection userCollection = null;
		DBObject dbObject=null;
			
		try {
				userCollection = userConn.getCollection("imdbUserWishList");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				System.out.println("ERROR: Connection not established!");
			}
		
		try {
			
			dbObject = (DBObject) JSON.parse(mapper.writeValueAsString(wish));
			System.out.println("DBObject value in String : "+dbObject);
			
			
		} catch (Exception e) {
			System.out.println("ERROR during mapping UserName/Password to Mongo Object");
			return "Error: During mapping User/Password.";
		}
		try{
			userCollection.insert(dbObject);
			
			return dbObject.get("imdbId").toString();
		}catch(Exception e){
			System.out.println("INSERT quesry not executing !");
			e.printStackTrace();
		}
		return null;
		
	}

	public static void main(String[] args) {
		CreateWishCommand create = new CreateWishCommand();
		WishList wish = new WishList();

		wish.setImdbId("1234ttt");
		wish.setDate(11,12,2005);
		Object id = create.execute(wish);
		
		if ( id!=null) {
			System.out.println("SUCCESS:Wish Created:"+id);
		} else {
			System.out.println("ERROR:Failed to create Wish");
		}

	}
}
