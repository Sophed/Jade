package systems.soph.jade.event;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import systems.soph.jade.Jade;
import systems.soph.jade.Palette;
import systems.soph.jade.party.Party;
import systems.soph.jade.party.PartyManager;
import systems.soph.jade.social.ChannelManager;
import systems.soph.jade.social.ChatChannel;
import systems.soph.jade.social.ChatFilter;
import systems.soph.jade.staff.StaffManager;

import static net.kyori.adventure.text.format.TextColor.color;

public class PlayerChat implements Listener {

    public PlayerChat(Jade p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void chat(AsyncChatEvent event) {

        Player player = event.getPlayer();
        String rawMsg = PlainTextComponentSerializer.plainText().serialize(event.message());

        ChatChannel channel = ChannelManager.getChannel(player);
        if (channel == null) {
            ChannelManager.setChannel(player, ChatChannel.GLOBAL);
            channel = ChatChannel.GLOBAL;
        }

        if (ChatFilter.filter(rawMsg)) {
            event.setCancelled(true);
            player.sendMessage(Component.text("Your message was blocked for containing a blacklisted word.")
                    .color(Palette.ERROR));
            return;
        }

        switch (channel) {

            case PARTY -> {
                event.setCancelled(true);
                Party party = PartyManager.getParty(player);
                if (party == null) {
                    player.sendMessage(Component.text("You are not in a party, switching to global chat.")
                            .color(Palette.ERROR));
                    ChannelManager.setChannel(player, ChatChannel.GLOBAL);
                    return;
                }
                party.partyChat(player, rawMsg);
                return;
            }

            case STAFF -> {
                event.setCancelled(true);
                StaffManager.staffChat(player, rawMsg);
                return;
            }

        }

        Component format = Component.text("PREFIX ")
                .append(Component.text(player.getName() + ": ")
                    .color(Palette.ACCENT)
                .append(Component.text(rawMsg)
                    .color(Palette.TEXT)));

        event.setCancelled(true);
        event.viewers().forEach(viewer -> viewer.sendMessage(format));

    }

}
