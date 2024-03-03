package systems.soph.jade.staff;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import systems.soph.jade.Palette;

public class ToggleAlertsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        StaffManager.toggleAlerts(player);

        return true;
    }

}
