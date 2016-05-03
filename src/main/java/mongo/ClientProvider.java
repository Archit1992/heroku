package mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

public class ClientProvider {
	
	MongoClient client=null;
	
	public MongoClient getClient(){
		// TODO Auto-generated method stub
		try {
			
			MongoClientURI uri = new MongoClientURI("mongodb://<db:user>:<db:password>@<db:port>.mlab.com:<db:port>/<db:name>");
			client = new MongoClient(uri); // MongoClient connected with the specified URI.

			return client;

		} catch (MongoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void connectionClose(){
		client.close();
	}
	
}
