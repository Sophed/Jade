package systems.soph.jade.staff.admin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import systems.soph.jade.Palette;
import systems.soph.jade.party.Party;
import systems.soph.jade.party.PartyManager;

import java.util.ArrayList;
import java.util.List;

public class PartyListCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        player.openInventory(invGUI());
        return true;
    }

    public Inventory invGUI() {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, Component.text("Party List")
                .color(Palette.ACCENT));
        List<Party> parties = PartyManager.getParties();
        for (Party party : parties) {
            if (inv.firstEmpty() == -1) {
                break;
            }
            inv.addItem(partyItem(party.getLeader(), party.getMembers()));
        }
        return inv;
    }

    public ItemStack partyItem(Player leader, List<Player> members) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(leader);
        meta.displayName(Component.text(leader.getName() + "'s Party")
                .color(Palette.ACCENT).decoration(TextDecoration.ITALIC, false));
        ArrayList<Component> lore = new ArrayList<>();
        for (Player member : members) {
            lore.add(Component.text(member.getName())
                    .color(Palette.TEXT).decoration(TextDecoration.ITALIC, false));
        }
        lore.add(Component.text("Members: " + members.size())
                .color(Palette.ACCENT).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

}
