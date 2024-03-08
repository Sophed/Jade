package systems.soph.jade;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import systems.soph.jade.entity.StatsManager;
import systems.soph.jade.register.CommandRegister;
import systems.soph.jade.register.EventRegister;
import systems.soph.jade.utils.EntityUtils;
import systems.soph.jade.utils.Mongo;

import java.util.logging.Logger;

public final class Jade extends JavaPlugin {

    static Jade INSTANCE;
    static Configuration CONFIG;
    public static Logger LOG;
    public static String MONGO_URI;
    public static String MONGO_NAME;
    public static Mongo MONGO;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        INSTANCE = this;
        CONFIG = getConfig();
        LOG = getLogger();
        LOG.info("Jade successfully loaded.");

        MONGO_URI = CONFIG.getString("database.uri");
        MONGO_NAME = CONFIG.getString("database.name");
        try {
            MONGO = new Mongo(MONGO_URI);
        } catch (Exception e) {
            LOG.severe("Failed to connect to MongoDB");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // Commands & Events
        new CommandRegister(this);
        new EventRegister(this);

        EntityUtils.loadEntities();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                Jade.getInstance(),
                () -> Bukkit.getOnlinePlayers().forEach(StatsManager::actionBarUpdate),
                0L, 20L
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LOG.info("Jade successfully disabled.");
    }

    public static Jade getInstance() {
        return INSTANCE;
    }

}
