package systems.soph.jade.item.impl;

import org.bukkit.Material;
import systems.soph.jade.item.CustomItem;
import systems.soph.jade.item.ItemRarity;
import systems.soph.jade.item.ItemType;

import java.util.List;

public class ObsidianDaggerItem extends CustomItem {

    public ObsidianDaggerItem() {
        super(
                "obsidian_dagger",
                "Obsidian Dagger",
                List.of("askldjasjlkdj"),
                Material.NETHERITE_SWORD,
                ItemType.SWORD,
                ItemRarity.LEGENDARY,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

}
