package systems.soph.jade.entity;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import systems.soph.jade.Palette;

import java.util.HashMap;

public class StatsManager {

    private static final HashMap<Entity, EntityStats> stats = new HashMap<>();

    public static void actionBarUpdate(Player player) {
        EntityStats entityStats = stats.get(player);
        int health = Math.max(0, HealthManager.getPretty(player));

        int maxHealth;
        if (entityStats == null) {
            maxHealth = 20;
        } else {
            maxHealth = entityStats.getMaxhealth();
        }

        Component msg = Component.text("❤ " + health + " / " + maxHealth)
                .color(Palette.ERROR);
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
        return new EntityStats(100, 0, 0, 0);
    }
    public static EntityStats defaultMobStats() {
        return new EntityStats(50, 0, 0, 0);
    }

}
