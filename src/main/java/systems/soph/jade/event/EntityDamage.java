package systems.soph.jade.event;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import systems.soph.jade.Jade;
import systems.soph.jade.entity.HealthManager;
import systems.soph.jade.entity.StatsManager;

public class EntityDamage implements Listener {

    public EntityDamage(Jade p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        double damage = event.getDamage();

        double maxHealth = StatsManager.getEntityStats(entity).getMaxhealth();
        double currentHealth = HealthManager.getEntityValue(entity);
        double newHealth = currentHealth - damage;
        HealthManager.setEntityValue(entity, Math.min(maxHealth, newHealth));

        LivingEntity livingEntity = (LivingEntity) entity;
        AttributeInstance attr = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double vanillaHealth = attr == null ? 20d : attr.getValue();

        if (newHealth <= 0) return;

        double scaledHealth = (newHealth / maxHealth) * vanillaHealth;
        scaledHealth = Math.max(0, Math.min(scaledHealth, vanillaHealth));

        event.setDamage(0);
        livingEntity.setHealth(scaledHealth);
    }

    @EventHandler
    public void heal(EntityRegainHealthEvent event) {
        Entity entity = event.getEntity();
        double damage = event.getAmount();

        double maxHealth = StatsManager.getEntityStats(entity).getMaxhealth();
        double currentHealth = HealthManager.getEntityValue(entity);
        double newHealth = currentHealth + damage;
        HealthManager.setEntityValue(entity, Math.min(maxHealth, newHealth));

        LivingEntity livingEntity = (LivingEntity) entity;
        AttributeInstance attr = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double vanillaHealth = attr == null ? 20d : attr.getValue();

        double scaledHealth = (newHealth / maxHealth) * vanillaHealth;
        scaledHealth = Math.max(0, Math.min(scaledHealth, vanillaHealth));

        if (newHealth <= 0) {
            return;
        }
        event.setAmount(0);
        livingEntity.setHealth(scaledHealth);
    }

}
