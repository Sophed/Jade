package systems.soph.jade.staff.admin.kits;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import systems.soph.jade.Palette;

import java.util.List;

public class KitCMD implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(Component.text("Usage: /kit <save|load|delete> <name>")
                    .color(Palette.ERROR));
            return true;
        }

        String action = args[0];
        String name = args[1];

        switch (action) {

            case "save" -> {
                if (KitManager.kitExists(player, name)) {
                    player.sendMessage(Component.text("Kit already exists.")
                            .color(Palette.ERROR));
                    return true;
                }
                KitManager.saveKit(player, name);
                player.sendMessage(Component.text("Saved kit: " + name)
                        .color(Palette.SUCCESS));
            }

            case "load" -> {
                ItemStack[] kit = KitManager.loadKit(player, name);
                if (kit == null) {
                    player.sendMessage(Component.text("Kit not found.")
                            .color(Palette.ERROR));
                    return true;
                }
                for (ItemStack item : kit) {
                    player.getInventory().addItem(item);
                }
                player.sendMessage(Component.text("Loaded kit: " + name)
                        .color(Palette.SUCCESS));
            }

            case "delete" -> {
                boolean kit = KitManager.deleteKit(player, name);
                if (!kit) {
                    player.sendMessage(Component.text("Kit not found.")
                            .color(Palette.ERROR));
                    return true;
                }
                player.sendMessage(Component.text("Deleted kit: " + name)
                        .color(Palette.SUCCESS));
            }

            default -> player.sendMessage(Component.text("Invalid action.")
                    .color(Palette.ERROR));

        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("save", "load", "delete");
        }
        if (args.length == 2) {
            if (!(sender instanceof Player player)) {
                return null;
            }
            return KitManager.getKitNames(player.getWorld());
        }
        return null;
    }
}
