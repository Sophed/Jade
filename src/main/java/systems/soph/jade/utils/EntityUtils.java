package systems.soph.jade.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import systems.soph.jade.entity.HealthManager;
import systems.soph.jade.entity.StatsManager;

public class EntityUtils {

    public static void loadEntities() {
        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e.isDead()) continue;
                LivingEntity le = (LivingEntity) e;

                StatsManager.addEntity(le, StatsManager.defaultMobStats());

                double maxHealth = StatsManager.getEntityStats(le).getMaxhealth();
                double currentHealth = HealthManager.getEntityValue(le);
                double newHealth = Math.min(maxHealth, currentHealth);

                AttributeInstance attr = le.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                double vanillaHealth = attr == null ? 20d : attr.getValue();

                HealthManager.setEntityValue(le, newHealth);
                double scaledHealth = (newHealth / maxHealth) * vanillaHealth;
                scaledHealth = Math.max(0, Math.min(scaledHealth, vanillaHealth));
                le.setHealth(scaledHealth);
            }
        }
    }

}
