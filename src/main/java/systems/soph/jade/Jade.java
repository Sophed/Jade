package systems.soph.jade;

import org.bukkit.plugin.java.JavaPlugin;
import systems.soph.jade.register.CommandRegister;
import systems.soph.jade.register.EventRegister;

import java.util.logging.Logger;

public final class Jade extends JavaPlugin {

    static Jade INSTANCE;
    public static Logger LOG;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        LOG = getLogger();
        LOG.info("Jade successfully loaded.");

        // Commands & Events
        new CommandRegister(this);
        new EventRegister(this);

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
