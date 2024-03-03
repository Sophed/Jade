package systems.soph.jade.register;

import org.bukkit.GameMode;
import systems.soph.jade.Jade;
import systems.soph.jade.item.ItemGetCMD;
import systems.soph.jade.party.PartyCMD;
import systems.soph.jade.social.ChannelCMD;
import systems.soph.jade.social.economy.GoldCMD;
import systems.soph.jade.social.levelling.LevelCMD;
import systems.soph.jade.staff.StaffChatCMD;
import systems.soph.jade.staff.ToggleAlertsCMD;
import systems.soph.jade.staff.admin.GamemodeCMD;
import systems.soph.jade.staff.admin.GamemodeQuickCMD;
import systems.soph.jade.staff.admin.PartyListCMD;
import systems.soph.jade.staff.admin.TestCMD;
import systems.soph.jade.staff.admin.kits.GiveKitCMD;
import systems.soph.jade.staff.admin.kits.KitCMD;

@SuppressWarnings("all")
public class CommandRegister {
    public CommandRegister(Jade p) {
        p.getCommand("party").setExecutor(new PartyCMD());
        p.getCommand("partylist").setExecutor(new PartyListCMD());
        p.getCommand("alerts").setExecutor(new ToggleAlertsCMD());
        p.getCommand("staffchat").setExecutor(new StaffChatCMD());
        p.getCommand("channel").setExecutor(new ChannelCMD());
        p.getCommand("gamemode").setExecutor(new GamemodeCMD());
        p.getCommand("gms").setExecutor(new GamemodeQuickCMD(GameMode.SURVIVAL));
        p.getCommand("gmc").setExecutor(new GamemodeQuickCMD(GameMode.CREATIVE));
        p.getCommand("gma").setExecutor(new GamemodeQuickCMD(GameMode.ADVENTURE));
        p.getCommand("gmsp").setExecutor(new GamemodeQuickCMD(GameMode.SPECTATOR));
        p.getCommand("kit").setExecutor(new KitCMD());
        p.getCommand("givekit").setExecutor(new GiveKitCMD());
        p.getCommand("gold").setExecutor(new GoldCMD());
        p.getCommand("level").setExecutor(new LevelCMD());
        p.getCommand("items").setExecutor(new ItemGetCMD());
        p.getCommand("test").setExecutor(new TestCMD());
    }
}
