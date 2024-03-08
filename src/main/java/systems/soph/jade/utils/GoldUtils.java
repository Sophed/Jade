package systems.soph.jade.utils;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonObject;
import systems.soph.jade.Jade;

import java.util.HashMap;
import java.util.UUID;

public class GoldUtils {

    private static final String COLLECTION = "gold";
    private static final HashMap<UUID, Integer> gold = new HashMap<>();

    public static void setGold(UUID uuid, int amount) {
        gold.put(uuid, amount);
    }
    public static int getGold(UUID uuid) {
        return gold.get(uuid);
    }
    public static void addGold(UUID uuid, int amount) {
        gold.put(uuid, gold.get(uuid) + Math.max(0, amount));
    }

    public static void loadGold(UUID uuid) {
        JsonObject res = Jade.MONGO.query(Jade.MONGO_NAME, COLLECTION, "{ \"uuid\": \"" + uuid + "\" }");
        if (res == null) {
            setGold(uuid, 0);
            return;
        }
        BsonDocument doc = res.toBsonDocument();
        setGold(uuid, doc.getInt32("amount").getValue());
    }

    public static void unloadGold(UUID uuid) {
        saveGold(uuid);
        gold.remove(uuid);
    }

    public static void saveGold(UUID uuid) {
        Document doc = new Document("uuid", uuid.toString()).append("amount", getGold(uuid));
        Jade.MONGO.delete(Jade.MONGO_NAME, COLLECTION, "{ \"uuid\": \"" + uuid + "\" }");
        Jade.MONGO.insert(Jade.MONGO_NAME, COLLECTION, doc);
    }


}
