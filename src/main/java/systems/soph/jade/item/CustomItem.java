package systems.soph.jade.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;
import systems.soph.jade.Palette;
import systems.soph.jade.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {

    String id;
    String name;
    List<String> description;
    Material material;
    ItemType type;
    ItemRarity rarity;
    boolean enchanted;
    ItemStats stats;
    String abilityLeftName;
    List<String> abilityLeftDescription;
    String abilityRightName;
    List<String> abilityRightDescription;

    public CustomItem(String id, String name, List<String> description, Material material, ItemType type, ItemRarity rarity, boolean enchanted, ItemStats stats, String abilityLeftName, List<String> abilityLeftDescription, String abilityRightName, List<String> abilityRightDescription) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.material = material;
        this.type = type;
        this.rarity = rarity;
        this.enchanted = enchanted;
        this.stats = stats;
        this.abilityLeftName = abilityLeftName;
        this.abilityLeftDescription = abilityLeftDescription;
        this.abilityRightName = abilityRightName;
        this.abilityRightDescription = abilityRightDescription;
    }

    public ItemStack getItem(int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name)
                .decoration(TextDecoration.ITALIC, false)
                .color(getColor()));

        ArrayList<Component> lore = new ArrayList<>();

        lore.add(Component.text(id)
                .decoration(TextDecoration.ITALIC, false)
                .color(Palette.ALT));

        lore.add(Component.text(""));

        lore.add(Component.text(StringUtils.title(rarity.name()) + " Item")
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true)
                .color(getColor()));

        lore.add(Component.text(""));

        for (String line : description) {
            lore.add(Component.text(line)
                    .decoration(TextDecoration.ITALIC, false)
                    .color(Palette.ALT));
        }

        lore.add(Component.text(""));

        if (abilityLeftName != null && abilityLeftDescription != null) {
            lore.add(Component.text("Ability [Left Click] - " + abilityLeftName)
                    .decoration(TextDecoration.ITALIC, false)
                    .decoration(TextDecoration.BOLD, true)
                    .color(Palette.ACCENT));

            for (String line : abilityLeftDescription) {
                lore.add(Component.text(line)
                        .decoration(TextDecoration.ITALIC, false)
                        .color(Palette.ALT));
            }

        }

        if (abilityRightName != null && abilityRightDescription != null) {
            lore.add(Component.text("Ability [Right Click] - " + abilityRightName)
                    .decoration(TextDecoration.ITALIC, false)
                    .decoration(TextDecoration.BOLD, true)
                    .color(Palette.ACCENT));

            for (String line : abilityRightDescription) {
                lore.add(Component.text(line)
                        .decoration(TextDecoration.ITALIC, false)
                        .color(Palette.ALT));
            }

        }

        meta.lore(lore);

        if (enchanted) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        item.setAmount(amount);
        return item;
    }

    public ItemStack getItem() {
        return getItem(1);
    }

    public @Nullable TextColor getColor() {
        return switch (rarity) {
            default -> TextColor.color(0xFFFFFF);
            case UNCOMMON -> TextColor.color(0x55FF55);
            case RARE -> TextColor.color(0x5583FF);
            case EPIC -> TextColor.color(0x9C55FF);
            case LEGENDARY -> TextColor.color(0xFFAA34);
            case MYTHIC -> TextColor.color(0xFF3373);
        };
    }

    public void leftAbility(PlayerInteractEvent event) {
        // Left Click Ability
    }

    public void rightAbility(PlayerInteractEvent event) {
        // Right Click Ability
    }

    public String getId() {
        return id;
    }

}
