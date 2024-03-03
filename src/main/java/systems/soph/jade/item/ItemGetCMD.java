package systems.soph.jade.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import systems.soph.jade.Palette;
import systems.soph.jade.item.impl.SpeedSugarItem;

import java.util.List;

public class ItemGetCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        Inventory inv = Bukkit.createInventory(null, 9 * 6, Component.text("Item List")
                .color(Palette.ACCENT));

        for (CustomItem item : items) {
            inv.addItem(item.getItem());
        }

        player.openInventory(inv);

        return true;
    }

    private static final List<CustomItem> items = List.of(
            new SpeedSugarItem()
    );

}
