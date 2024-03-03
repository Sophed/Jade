package systems.soph.jade.staff.admin.kits;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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

public class GiveKitCMD implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length < 2) {
            sender.sendMessage(Component.text("Specify a player and a kit.")
                    .color(Palette.ERROR));
            return true;
        }

        String playerName = args[0];
        String kitName = args[1];

        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(Component.text("Player not found.")
                    .color(Palette.ERROR));
            return true;
        }
        ItemStack[] kit = KitManager.loadKit(player, kitName);
        if (kit == null) {
            sender.sendMessage(Component.text("Kit not found.")
                    .color(Palette.ERROR));
            return true;
        }
        sender.sendMessage(Component.text("Gave kit: " + kitName + " to " + playerName)
                .color(Palette.SUCCESS));

        for (ItemStack item : kit) {
            player.getInventory().addItem(item);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 2) {
            return KitManager.getKitNames(Bukkit.getWorlds().get(0));
        }
        return null;
    }
}
