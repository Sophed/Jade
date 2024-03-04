package systems.soph.jade.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import systems.soph.jade.Jade;
import systems.soph.jade.entity.HealthManager;
import systems.soph.jade.entity.StatsManager;

public class EntityLifetimes implements Listener {

    public EntityLifetimes(Jade p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    @EventHandler
    public void create(EntitySpawnEvent event) {
        HealthManager.setEntityValue(event.getEntity(), StatsManager.defaultMobStats().getMaxhealth());
        StatsManager.addEntity(event.getEntity(), StatsManager.defaultMobStats());
    }

    @EventHandler
    public void remove(EntityDeathEvent event) {
        HealthManager.removeEntity(event.getEntity());
        StatsManager.removeEntity(event.getEntity());
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event) {
        HealthManager.setEntityValue(event.getPlayer(), StatsManager.defaultPlayerStats().getMaxhealth());
        StatsManager.addEntity(event.getPlayer(), StatsManager.defaultPlayerStats());
    }

}
