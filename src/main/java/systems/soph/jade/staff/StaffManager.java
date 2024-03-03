package systems.soph.jade.staff;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import systems.soph.jade.Palette;

import java.util.ArrayList;

public class StaffManager {

    public static final String STAFF_PERMISSION = "core.staff";

    private static ArrayList<Player> alertsEnabled = new ArrayList<>();

    public static void toggleAlerts(Player player) {
        if (alertsEnabled.contains(player)) {
            alertsEnabled.remove(player);
            player.sendMessage(Component.text("Staff alerts disabled.").color(Palette.ERROR));
        } else {
            alertsEnabled.add(player);
            player.sendMessage(Component.text("Staff alerts enabled.").color(Palette.SUCCESS));
        }
    }

    public static void setAlertsEnabled(Player player, boolean enabled) {
        if (enabled) {
            alertsEnabled.add(player);
        } else {
            alertsEnabled.remove(player);
        }
    }

    public static void alert(Component message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission(STAFF_PERMISSION)) continue;
            if (!alertsEnabled.contains(p)) continue;
            p.sendMessage(Component.text("[Staff] ").color(Palette.ACCENT).append(message));
        }
    }

    public static void message(Component message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission(STAFF_PERMISSION)) continue;
            p.sendMessage(Component.text("[Staff] ").color(Palette.ACCENT).append(message));
        }
    }

    public static void staffChat(Player player, String message) {
        message(Component.text(player.getName() + ": ")
                .color(Palette.ACCENT)
                .append(Component.text(message)
                .color(Palette.TEXT)));
    }

}
