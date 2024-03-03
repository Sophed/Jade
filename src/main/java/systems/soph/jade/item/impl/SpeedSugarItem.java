package systems.soph.jade.item.impl;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import systems.soph.jade.item.CustomItem;
import systems.soph.jade.item.ItemRarity;
import systems.soph.jade.item.ItemType;

import java.util.List;

public class SpeedSugarItem extends CustomItem {

    public SpeedSugarItem() {
        super(
                "speed_sugar",
                "Speed Sugar",
                List.of("A mysterious white powder that just", "might give you a speed boost."),
                Material.SUGAR,
                ItemType.CONSUMABLE,
                ItemRarity.UNCOMMON,
                true,
                null,
                null,
                null,
                "Speed Boost",
                List.of("Gives you a speed boost for 5 seconds.")
        );
    }

    @Override
    public void rightAbility(PlayerInteractEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 3, 1));
        event.getItem().setAmount(event.getItem().getAmount() - 1);
    }

}
