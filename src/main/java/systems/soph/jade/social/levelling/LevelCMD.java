package systems.soph.jade.social.levelling;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import systems.soph.jade.Palette;

import java.util.ArrayList;
import java.util.List;

public class LevelCMD implements CommandExecutor, TabExecutor {

    private static final String PERMISSION = "core.level.manage";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Component.text("You are level " + LevelManager.getLevel(player) + " with " + LevelManager.getXp(player) + " xp.")
                    .color(Palette.ACCENT));
            return true;
        }

        if (!player.hasPermission(PERMISSION)) {
            player.sendMessage(Component.text("You do not have permission to use any subcommands.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length < 3) {
            player.sendMessage(Component.text("Usage: /level <setlevel|addxp|setxp|getxp|getlevel> <player> <amount>")
                    .color(Palette.ERROR));
            return true;
        }

        Player target = player.getServer().getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(Component.text("Player not found.")
                    .color(Palette.ERROR));
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(Component.text("Invalid amount.")
                    .color(Palette.ERROR));
            return true;
        }

        switch (args[0]) {
            case "setlevel" -> {
                LevelManager.setLevel(target, amount);
                player.sendMessage(Component.text("Set " + target.getName() + "'s level to " + amount + ".")
                        .color(Palette.TEXT));
            }
            case "addxp" -> {
                player.sendMessage(Component.text("Added " + amount + " xp to " + target.getName() + ".")
                        .color(Palette.TEXT));
                LevelManager.addXp(target, amount);
            }
            case "setxp" -> {
                player.sendMessage(Component.text("Set " + target.getName() + "'s xp to " + amount + ".")
                        .color(Palette.TEXT));
                LevelManager.setXp(target, amount);
            }
            default -> {
                player.sendMessage(Component.text("Invalid subcommand.")
                        .color(Palette.ERROR));
                return true;
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            return null;
        }
        if (args.length == 1) {
            return List.of("setlevel", "addxp", "setxp");
        }
        if (args.length == 2) {
            ArrayList<String> players = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                players.add(player.getName());
            }
            return players;
        }
        return null;
    }

}
