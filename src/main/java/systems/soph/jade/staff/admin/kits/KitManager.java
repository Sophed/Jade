package systems.soph.jade.staff.admin.kits;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import systems.soph.jade.Jade;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class KitManager {

    public static void saveKit(Player player, String name) {
        PersistentDataContainer data = player.getWorld().getPersistentDataContainer();
        NamespacedKey kitKey = new NamespacedKey(Jade.getInstance(), "core-kit-" + name);

        ItemStack[] items = player.getInventory().getContents();
        ArrayList<String> itemList = new ArrayList<>();

        for (ItemStack item : items) {
            if (item != null && item.getType() != Material.AIR) {
                itemList.add(byteArrayToBase64(item.serializeAsBytes()));
            }
        }
        String itemsString = String.join(";", itemList);
        data.set(kitKey, PersistentDataType.STRING, itemsString);
    }

    public static ItemStack[] loadKit(Player player, String name) {
        PersistentDataContainer data = player.getWorld().getPersistentDataContainer();
        NamespacedKey kitKey = new NamespacedKey(Jade.getInstance(), "core-kit-" + name);

        String itemsString = data.get(kitKey, PersistentDataType.STRING);
        if (itemsString == null) {
            return null;
        }

        String[] itemList = itemsString.split(";");
        List<ItemStack> items = new ArrayList<>();
        for (String itemString : itemList) {
            items.add(ItemStack.deserializeBytes(base64ToByteArray(itemString)));
        }
        return items.toArray(new ItemStack[0]);
    }

    public static boolean deleteKit(Player player, String name) {
        PersistentDataContainer data = player.getWorld().getPersistentDataContainer();
        NamespacedKey kitKey = new NamespacedKey(Jade.getInstance(), "core-kit-" + name);
        if (data.has(kitKey, PersistentDataType.STRING)) {
            data.remove(kitKey);
            return true;
        }
        return false;
    }

    public static boolean kitExists(Player player, String name) {
        PersistentDataContainer data = player.getWorld().getPersistentDataContainer();
        NamespacedKey kitKey = new NamespacedKey(Jade.getInstance(), "core-kit-" + name);
        return data.has(kitKey, PersistentDataType.STRING);
    }

    public static List<String> getKitNames(World world) {
        PersistentDataContainer data = world.getPersistentDataContainer();
        List<String> kitNames = new ArrayList<>();
        for (NamespacedKey key : data.getKeys()) {
            if (key.getKey().startsWith("core-kit-")) {
                kitNames.add(key.getKey().substring(9));
            }
        }
        return kitNames;
    }

    private static String byteArrayToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] base64ToByteArray(String base64) {
        return Base64.getDecoder().decode(base64);
    }

}
