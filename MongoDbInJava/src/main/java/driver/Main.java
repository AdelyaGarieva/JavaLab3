package driver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create();
        MongoDatabase database = client.getDatabase("shop");
        MongoCollection<Document> collection = database.getCollection("user");

        Document doc = new Document("firstName", "Adelya")
                .append("lastName", "Garieva")
                .append("login", "adelya")
                .append("password", "adelya");
        collection.insertOne(doc);

        collection.updateOne(eq("login", "adelya"), new Document("$set", new Document("login", "delya_g")));

        collection.deleteOne(eq("login", "delya_g"));
    }
}
