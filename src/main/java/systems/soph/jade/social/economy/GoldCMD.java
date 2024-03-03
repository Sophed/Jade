package systems.soph.jade.social.economy;

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

public class GoldCMD implements CommandExecutor, TabExecutor {

    private static final String PERMISSION = "core.gold.manage";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Component.text("You have " + GoldManager.getGold(player) + " gold.")
                    .color(Palette.ACCENT));
            return true;
        }

        if (!player.hasPermission(PERMISSION)) {
            player.sendMessage(Component.text("You do not have permission to use any subcommands.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length < 3) {
            player.sendMessage(Component.text("Usage: /gold <set|add|remove|get> <player> <amount>")
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
            case "set" -> {
                player.sendMessage(Component.text("You set " + target.getName() + "'s gold to " + amount + ".")
                        .color(Palette.ACCENT));
                GoldManager.setGold(target, amount);
            }
            case "add" -> {
                player.sendMessage(Component.text("You added " + amount + " gold to " + target.getName() + ".")
                        .color(Palette.ACCENT));
                GoldManager.addGold(target, amount);
            }
            case "remove" -> {
                player.sendMessage(Component.text("You removed " + amount + " gold from " + target.getName() + ".")
                        .color(Palette.ACCENT));
                GoldManager.removeGold(target, amount);
            }
            case "get" -> player.sendMessage(Component.text(target.getName() + " has " + GoldManager.getGold(target) + " gold.")
                    .color(Palette.ACCENT));
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
            return List.of("set", "add", "remove", "get");
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
