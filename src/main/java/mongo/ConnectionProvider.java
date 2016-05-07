package mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

import util.PropertiesLookup;

public class ConnectionProvider {

	MongoClient client = null;
	private static String URI = null;

	public DBCollection getCollection(String collectionName) throws UnknownHostException {
		// TODO Auto-generated method stub
		try {
			
			// getting a data from Properties file.
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

			@SuppressWarnings("deprecation")
			DB db = client.getDB(uri.getDatabase()); // Database Object created.

			if (db == null) {
				System.out.println("Could not connect to Database");
			}

			DBCollection userColl = db.getCollection(collectionName); // send back the Collection Object.
			return userColl;

		} catch (MongoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		ConnectionProvider connect = new ConnectionProvider();
		try {
			DBCollection collectionName = connect.getCollection("imdbUser");	// for testing purposes..
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
