package mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

import util.PropertiesLookup;

public class ClientProvider {
	
	MongoClient client=null;
	private static String URI = null;
	public MongoClient getClient(){
		// TODO Auto-generated method stub
		try {
			// getting a data from the Properties file...
			PropertiesLookup lookup = new PropertiesLookup();
			URI = "mongodb://" 
					+lookup.getProperty("dbuser") + ":"
					+lookup.getProperty("dbpassword") + "@ds"
					+lookup.getProperty("dbport") + ".mlab.com:" 
					+lookup.getProperty("dbport") + "/"
					+lookup.getProperty("dbname");
			System.out.println("URI is :"+URI);
			
			MongoClientURI uri = new MongoClientURI(URI);
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
	public static void main(String[] args) {
		ConnectionProvider connect = new ConnectionProvider();
		try {
			DBCollection collectionName = connect.getCollection("imdbUser"); // for testing purposes...
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
