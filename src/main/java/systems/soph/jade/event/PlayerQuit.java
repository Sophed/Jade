package systems.soph.jade.event;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import systems.soph.jade.Jade;
import systems.soph.jade.Palette;
import systems.soph.jade.entity.HealthManager;
import systems.soph.jade.entity.StatsManager;
import systems.soph.jade.party.Party;
import systems.soph.jade.party.PartyManager;
import systems.soph.jade.social.ChannelManager;
import systems.soph.jade.staff.StaffManager;

public class PlayerQuit implements Listener {

    public PlayerQuit(Jade p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    @EventHandler
    public void join(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.quitMessage(null);

        if (player.hasPermission("core.staff")) {
            StaffManager.setAlertsEnabled(player, false);
        }

        Party party = PartyManager.getParty(player);
        if (party != null) {
            party.removeMember(player);
            party.message(Component.text(player.getName() + " left the server and was removed from the party.")
                    .color(Palette.ERROR));
        }

        ChannelManager.removeChannel(player);
        StatsManager.removeEntity(player);
        HealthManager.removeEntity(player);

    }

}
