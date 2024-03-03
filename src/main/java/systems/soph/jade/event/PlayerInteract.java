package systems.soph.jade.event;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import systems.soph.jade.Jade;
import systems.soph.jade.item.CustomItem;
import systems.soph.jade.item.impl.SpeedSugarItem;

import java.util.List;

public class PlayerInteract implements Listener {

    public PlayerInteract(Jade p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void interact(PlayerInteractEvent event) {

        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;
        ItemMeta meta = event.getItem().getItemMeta();
        if (!meta.hasLore()) return;
        String id = PlainTextComponentSerializer.plainText().serialize(meta.lore().get(0));

        for (CustomItem item : interactions) {
            if (item.getId().equals(id)) {
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    item.leftAbility(event);
                    event.setCancelled(true);
                } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    item.rightAbility(event);
                    event.setCancelled(true);
                }
                return;
            }
        }

    }

    public static List<CustomItem> interactions = List.of(
            new SpeedSugarItem()
    );

}
