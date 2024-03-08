package systems.soph.jade.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Mongo {

    String uri;
    MongoClient client;

    public Mongo(String uri) {
        this.uri = uri;
        try {
            client = MongoClients.create(uri);
            client.listDatabaseNames().first(); // Test connection
            Bukkit.getConsoleSender().sendMessage(
                    Component.text("Successfully connected to MongoDB",
                            TextColor.color(0x00FF00))
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to MongoDB");
        }
    }

    public JsonObject query(String database, String collection, String query) {
        try {
            MongoDatabase db = client.getDatabase(database);
            MongoCollection<Document> col = db.getCollection(collection);

            Document doc = Document.parse(query);
            if (doc == null) {
                return null;
            }
            Document result = col.find(doc).first();
            if (result == null) {
                return null;
            }
            return new JsonObject(result.toJson());
        } catch (Exception e) {
            return null;
        }
    }

    public List<JsonObject> getAll(String database, String collection) {
        try {
            MongoDatabase db = client.getDatabase(database);
            MongoCollection<Document> col = db.getCollection(collection);

            List<Document> results = col.find().into(new ArrayList<>());
            if (results.isEmpty()) {
                return null;
            }
            ArrayList<JsonObject> jsonResults = new ArrayList<>();
            results.forEach(d -> jsonResults.add(new JsonObject(d.toJson())));
            return jsonResults;
        } catch (Exception ignored) {
            return null;
        }
    }

    public void insert(String database, String collection, Document document) {
        try {
            MongoDatabase db = client.getDatabase(database);
            MongoCollection<Document> col = db.getCollection(collection);
            col.insertOne(document);
        } catch (Exception ignored) {
        }
    }

    public void delete(String database, String collection, String query) {
        try {
            MongoDatabase db = client.getDatabase(database);
            MongoCollection<Document> col = db.getCollection(collection);
            Document doc = Document.parse(query);
            if (doc == null) {
                return;
            }
            col.deleteOne(doc);
        } catch (Exception ignored) {
        }
    }

}
