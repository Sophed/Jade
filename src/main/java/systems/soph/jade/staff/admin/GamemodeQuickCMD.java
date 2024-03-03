package systems.soph.jade.staff.admin;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import systems.soph.jade.Palette;

public class GamemodeQuickCMD implements CommandExecutor {

    private final GameMode mode;

    public GamemodeQuickCMD(GameMode mode) {
        this.mode = mode;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        Player target;
        if (args.length == 0) {
            target = player;
        } else {
            target = player.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Component.text("Player not found.")
                        .color(Palette.ERROR));
                return true;
            }
            player.sendMessage(Component.text("Set " + target.getName() + " to " + capitalize(mode.name()))
                    .color(Palette.SUCCESS));
        }

        GamemodeCMD.setMode(target, mode);

        return true;
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}
