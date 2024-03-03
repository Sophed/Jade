package systems.soph.jade.event;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import systems.soph.jade.Jade;
import systems.soph.jade.Palette;
import systems.soph.jade.social.ChannelManager;
import systems.soph.jade.social.ChatChannel;
import systems.soph.jade.social.levelling.LevelManager;
import systems.soph.jade.staff.StaffManager;

public class PlayerJoin implements Listener {

    public PlayerJoin(Jade p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("core.staff")) {
            StaffManager.setAlertsEnabled(player, true);
        }

        event.joinMessage(null);
        String clientBrand = player.getClientBrandName();

        StaffManager.alert(Component.text(player.getName() + " joined with '" + clientBrand + "'")
                .color(Palette.SUCCESS));

        ChannelManager.setChannel(player, ChatChannel.GLOBAL);
        LevelManager.updateXpBar(player);

    }

}
