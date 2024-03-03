package systems.soph.jade.staff.admin;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import systems.soph.jade.Palette;

import java.util.List;

public class GamemodeCMD implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Component.text("Usage: /gamemode <creative|survival|adventure|spectator>")
                    .color(Palette.ERROR));
            return true;
        }

        GameMode mode;
        switch (args[0]) {
            case "creative" -> mode = GameMode.CREATIVE;
            case "survival" -> mode = GameMode.SURVIVAL;
            case "adventure" -> mode = GameMode.ADVENTURE;
            case "spectator" -> mode = GameMode.SPECTATOR;
            default -> {
                sender.sendMessage(Component.text("Invalid gamemode.")
                        .color(Palette.ERROR));
                return true;
            }
        }

        Player target;
        if (args.length == 1) {
            target = player;
        } else {
            target = player.getServer().getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(Component.text("Player not found.")
                        .color(Palette.ERROR));
                return true;
            }
            player.sendMessage(Component.text("Set " + target.getName() + " to " + capitalize(mode.name()))
                    .color(Palette.SUCCESS));
        }

        setMode(target, mode);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of(
                    "creative",
                    "survival",
                    "adventure",
                    "spectator"
            );
        }
        return null;
    }

    public static void setMode(Player player, GameMode mode) {
        player.setGameMode(mode);
        player.sendMessage(Component.text("You are now in " + capitalize(mode.name()))
                .color(Palette.SUCCESS));
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}
