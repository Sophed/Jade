package systems.soph.jade.social.economy;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import systems.soph.jade.Jade;

public class GoldManager {

    private static final NamespacedKey goldKey = new NamespacedKey(Jade.getInstance(), "core-gold");

    public static void setGold(Player player, int amount) {
        player.getPersistentDataContainer().set(goldKey, PersistentDataType.INTEGER, amount);
        if (getGold(player) < 0) setGold(player, 0);
    }

    public static int getGold(Player player) {
        return player.getPersistentDataContainer().getOrDefault(goldKey, PersistentDataType.INTEGER, 0);
    }

    public static void addGold(Player player, int amount) {
        setGold(player, getGold(player) + amount);
    }

    public static void removeGold(Player player, int amount) {
        setGold(player, getGold(player) - amount);
        if (getGold(player) < 0) setGold(player, 0);
    }

}
