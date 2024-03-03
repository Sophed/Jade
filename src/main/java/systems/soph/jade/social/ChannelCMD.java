package systems.soph.jade.social;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import systems.soph.jade.Palette;
import systems.soph.jade.staff.StaffManager;

import java.util.ArrayList;
import java.util.List;

public class ChannelCMD implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length == 0) {
            String channels = "<global|party";
            if (player.hasPermission(StaffManager.STAFF_PERMISSION)) {
                channels += "|staff";
            }
            channels += ">";
            sender.sendMessage(Component.text("Usage: " + channels)
                    .color(Palette.ERROR));
            return true;
        }

        switch (args[0]) {
            case "global" -> {
                ChannelManager.setChannel(player, ChatChannel.GLOBAL);
                player.sendMessage(Component.text("You are now in global chat.")
                        .color(Palette.SUCCESS));
            }
            case "party" -> {
                ChannelManager.setChannel(player, ChatChannel.PARTY);
                player.sendMessage(Component.text("You are now in party chat.")
                        .color(Palette.SUCCESS));
            }
            case "staff" -> {
                if (!player.hasPermission(StaffManager.STAFF_PERMISSION)) {
                    player.sendMessage(Component.text("You do not have permission to use staff chat.")
                            .color(Palette.ERROR));
                    return true;
                }
                ChannelManager.setChannel(player, ChatChannel.STAFF);
                player.sendMessage(Component.text("You are now in staff chat.")
                        .color(Palette.SUCCESS));
            }
            default -> {
                sender.sendMessage(Component.text("Invalid channel.")
                        .color(Palette.ERROR));
                return true;
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            ArrayList<String> completions = new ArrayList<>();
            completions.add("global");
            completions.add("party");
            if (sender.hasPermission(StaffManager.STAFF_PERMISSION)) {
                completions.add("staff");
            }
            return completions;
        }
        return null;
    }

}
