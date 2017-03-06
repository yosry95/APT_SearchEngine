import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class Database {
	
	MongoClient mongoClient ;
	MongoDatabase database ;
	MongoCollection<Document> collection ;
	
	Block<Document> printBlock = new Block<Document>() {
	       @Override
	       public void apply(final Document document) {
	           System.out.println(document.toJson());
	       }
	};

	public boolean connect() {
		try {
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("mydb");
		//set timeout to 3 seconds
		database.withWriteConcern(new WriteConcern(3000));
		collection = database.getCollection("restaurants");
		return true;
		}
		catch (Exception exp){
			return false;
		}
	}
	
	public boolean createDocument (String name){
		Document document = new Document("name", "Café Con Leche")
	               .append("contact", new Document("phone", "228-555-0149")
	                                       .append("email", "cafeconleche@example.com")
	                                       .append("location",Arrays.asList(-73.92502, 40.8279556)))
	               .append("stars", 3)
	               .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));

		collection.insertOne(document);
		return true;
	}
	
	public boolean updateDocument(long id) {
		collection.updateOne(
                eq("_id", new ObjectId("57506d62f57802807471dd41")),
                combine(set("stars", 1), set("contact.phone", "228-555-9999"), currentDate("lastModified")));
		return true;
	}
	
	public boolean deleteDocument(long id) {
		collection.deleteOne(eq("_id", new ObjectId("57506d62f57802807471dd41")));
		return true;
	}
	
	public void findDocument() {
		// a query written without helpers
		collection.find(new Document("stars", new Document("$gte", 2)
			          	.append("$lt", 5))
			          	.append("categories", "Bakery")).forEach(printBlock);
		
		// a query written with helpers
		collection.find(and(gte("stars", 2), lt("stars", 5), eq("categories", "Bakery")))
        .forEach(printBlock);
		
		// a projection written without helpers
		collection.find(and(gte("stars", 2), lt("stars", 5), eq("categories", "Bakery")))
        .projection(new Document("name", 1)
             .append("stars", 1)
             .append("categories",1)
             .append("_id", 0))
        .forEach(printBlock);
		
		// a projection written with helpers
		collection.find(and(gte("stars", 2), lt("stars", 5), eq("categories", "Bakery")))
        .projection(fields(include("name", "stars", "categories"), excludeId()))
        .forEach(printBlock);
		
		
	}
}
