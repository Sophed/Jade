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

}
