package systems.soph.jade.entity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import systems.soph.jade.Jade;
import systems.soph.jade.Palette;

import java.util.HashMap;

public class StatsManager {

    private static final String COLLECTION = "stats";
    private static final String KEY_HEALTH = "baseHealth";
    private static final String KEY_DEFENSE = "baseDefense";
    private static final String KEY_INTELLECT = "baseIntellect";
    private static final String KEY_STRENGTH = "baseStrength";
    private static final String KEY_ENERGY = "baseEnergy";
    private static final HashMap<Entity, EntityStats> stats = new HashMap<>();

    public static void actionBarUpdate(Player player) {
        EntityStats entityStats = stats.get(player);
        int health = Math.max(0, HealthManager.getPretty(player));
        int mana = Math.max(0, ManaManager.getPretty(player));
        int energy = Math.max(0, EnergyManager.getPretty(player));

        int maxHealth;
        int maxMana;
        int maxEnergy;
        if (entityStats == null) {
            maxHealth = 20;
            maxMana = 0;
            maxEnergy = 100;
        } else {
            maxHealth = entityStats.getMaxhealth();
            maxMana = entityStats.getIntellect();
            maxEnergy = entityStats.getEnergy();
        }

        Component msg = Component.text("❤ " + health + " / " + maxHealth)
                .color(Palette.ERROR)
                .append(Component.text("   "))
                .append(Component.text("⭐ " + mana + " / " + maxMana)
                        .color(Palette.ACCENT))
                        .append(Component.text("   "))
                        .append(Component.text("⚡ " + energy + " / " + maxEnergy)
                                .color(Palette.WARNING));
        player.sendActionBar(msg);
    }

    public static void addEntity(Entity entity, EntityStats entityStats) {
        stats.put(entity, entityStats);
    }
    public static void removeEntity(Entity entity) {
        stats.remove(entity);
    }
    public static EntityStats getEntityStats(Entity entity) {
        return stats.get(entity);
    }
    public static void setEntityStats(Entity entity, EntityStats entityStats) {
        stats.put(entity, entityStats);
    }

    public static EntityStats defaultPlayerStats() {
        return new EntityStats(100, 0, 100, 0, 100);
    }
    public static EntityStats defaultMobStats() {
        return new EntityStats(50, 0, 50, 0, 50);
    }

    public static void loadStats(Player player) {
        JsonObject res = Jade.MONGO.query(Jade.MONGO_NAME, COLLECTION, "{ \"uuid\": \"" + player.getUniqueId() + "\" }");
        if (res == null) {
            setEntityStats(player, defaultPlayerStats());
            return;
        }
        BsonDocument doc = res.toBsonDocument();
        setEntityStats(player, new EntityStats(
                doc.getInt32(KEY_HEALTH).getValue(),
                doc.getInt32(KEY_DEFENSE).getValue(),
                doc.getInt32(KEY_INTELLECT).getValue(),
                doc.getInt32(KEY_STRENGTH).getValue(),
                doc.getInt32(KEY_ENERGY).getValue())
        );
    }

    public static void unloadStats(Player player) {
        saveStats(player);
        removeEntity(player);
    }

    public static void saveStats(Player player) {
        EntityStats entityStats = getEntityStats(player);
        if (entityStats == null) {
            return;
        }
        Document doc = new Document()
                .append("uuid", player.getUniqueId().toString())
                .append(KEY_HEALTH, entityStats.getMaxhealth())
                .append(KEY_DEFENSE, entityStats.getDefense())
                .append(KEY_INTELLECT, entityStats.getIntellect())
                .append(KEY_STRENGTH, entityStats.getStrength())
                .append(KEY_ENERGY, entityStats.getEnergy());
        Jade.MONGO.delete(Jade.MONGO_NAME, COLLECTION, "{ \"uuid\": \"" + player.getUniqueId() + "\" }");
        Jade.MONGO.insert(Jade.MONGO_NAME, COLLECTION, doc);
    }

}
