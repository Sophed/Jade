package systems.soph.jade.staff.admin;

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
import systems.soph.jade.event.ExploitPrevention;

import java.util.List;

public class TestCMD implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Component.text("Provide a subcommand.")
                    .color(Palette.ERROR));
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "clear-crafting-grid" -> {
                Player target = player;
                if (args.length > 1) {
                    target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(Component.text("Player not found.")
                                .color(Palette.ERROR));
                        return true;
                    }
                }
                player.sendMessage(Component.text("Clearing...")
                        .color(Palette.SUCCESS));
                ExploitPrevention.clearCraftingGrid(target);
            }

            default -> player.sendMessage(Component.text("Invalid subcommand.")
                    .color(Palette.ERROR));

        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of(
                    "clear-crafting-grid"
            );
        }
        return null;
    }

}
