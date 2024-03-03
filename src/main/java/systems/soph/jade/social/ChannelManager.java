package systems.soph.jade.social;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChannelManager {

    public static HashMap<Player, ChatChannel> playerChannels = new HashMap<>();

    public static void setChannel(Player player, ChatChannel channel) {
        playerChannels.put(player, channel);
    }

    public static ChatChannel getChannel(Player player) {
        return playerChannels.get(player);
    }

    public static void removeChannel(Player player) {
        playerChannels.remove(player);
    }

}
