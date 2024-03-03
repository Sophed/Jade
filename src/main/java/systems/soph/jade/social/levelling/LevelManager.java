package systems.soph.jade.social.levelling;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import systems.soph.jade.Jade;
import systems.soph.jade.Palette;

public class LevelManager {

    private static final NamespacedKey levelKey = new NamespacedKey(Jade.getInstance(), "core-level");
    private static final NamespacedKey xpKey = new NamespacedKey(Jade.getInstance(), "core-xp");

    private static final int baseXp = 2000;
    private static final Sound xpSound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
    private static final Sound levelSound = Sound.ENTITY_PLAYER_LEVELUP;

    public static int getXpToNextLevel(Player player) {
        int level = getLevel(player);
        return (baseXp + (150 * level)) - getXp(player);
    }

    public static void addXp(Player player, int xp) {
        int level = getLevel(player);
        int currentXp = getXp(player) + xp;
        if (currentXp >= getXpToNextLevel(player)) {
            setLevel(player, level + 1);
            setXp(player, currentXp - getXpToNextLevel(player));
            player.playSound(player.getLocation(), levelSound, 1, 1);
            player.sendMessage(Component.text("Level up!").color(Palette.ACCENT).decorate(TextDecoration.BOLD));
            player.sendMessage(Component.text("You are now level " + getLevel(player) + "!").color(Palette.ACCENT));
        } else {
            setXp(player, currentXp);
            player.playSound(player.getLocation(), xpSound, 1, 1);
        }
        updateXpBar(player);
    }

    public static void setLevel(Player player, int level) {
        player.getPersistentDataContainer().set(levelKey, PersistentDataType.INTEGER, level);
    }
    public static int getLevel(Player player) {
        return player.getPersistentDataContainer().getOrDefault(levelKey, PersistentDataType.INTEGER, 0);
    }
    public static void setXp(Player player, int xp) {
        player.getPersistentDataContainer().set(xpKey, PersistentDataType.INTEGER, xp);
        updateXpBar(player);
    }
    public static int getXp(Player player) {
        return player.getPersistentDataContainer().getOrDefault(xpKey, PersistentDataType.INTEGER, 0);
    }

    public static void updateXpBar(Player player) {
        player.setLevel(getLevel(player));
        player.setExp(0);
        Bukkit.getScheduler().runTaskLater(Jade.getInstance(), () -> {
            int xp = getXp(player);
            int xpAsPercentageOfTotalNeeded = xp * 100 / getXpToNextLevel(player);
            int xpStep = player.getExpToLevel() / 100;
            player.setExp(xpStep * xpAsPercentageOfTotalNeeded);
        }, 1L);
    }

}
