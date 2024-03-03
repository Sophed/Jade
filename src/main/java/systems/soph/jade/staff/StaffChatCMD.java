package systems.soph.jade.staff;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import systems.soph.jade.Palette;
import systems.soph.jade.social.ChannelManager;
import systems.soph.jade.social.ChatChannel;

public class StaffChatCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length == 0) {
            ChatChannel channel = ChannelManager.getChannel(player);
            if (channel == ChatChannel.STAFF) {
                ChannelManager.setChannel(player, ChatChannel.GLOBAL);
                player.sendMessage(Component.text("You are now in global chat.")
                        .color(Palette.ERROR));
            } else {
                ChannelManager.setChannel(player, ChatChannel.STAFF);
                player.sendMessage(Component.text("You are now in staff chat.")
                        .color(Palette.SUCCESS));
            }
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }
        message.deleteCharAt(message.length() - 1);

        StaffManager.staffChat(player, message.toString());


        return true;
    }
}
