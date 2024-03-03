package systems.soph.jade.party;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import systems.soph.jade.Palette;

import java.util.ArrayList;

public class Party {

    private Player leader;
    private ArrayList<Player> members = new ArrayList<>();
    private ArrayList<Player> invites = new ArrayList<>();

    public Party(Player leader) {
        this.leader = leader;
        members.add(leader);
    }

    public void message(Component message) {
        for (Player member : members) {
            member.sendMessage(Component.text("[Party] ").color(Palette.ACCENT).append(message));
        }
    }

    public void partyChat(Player player, String message) {
        message(Component.text(player.getName() + ": ")
                .color(Palette.ACCENT)
                .append(Component.text(message)
                .color(Palette.TEXT)));
    }

    public void addMember(Player member) {
        members.add(member);
    }

    public void removeMember(Player member) {
        members.remove(member);
    }

    public void addInvite(Player player) {
        invites.add(player);
    }

    public void removeInvite(Player player) {
        invites.remove(player);
    }

    public boolean hasInvite(Player player) {
        return invites.contains(player);
    }

    public Player getLeader() {
        return leader;
    }

    public ArrayList<Player> getMembers() {
        return members;
    }

    public boolean isMember(Player member) {
        return members.contains(member);
    }

    public boolean isLeader(Player member) {
        return leader.equals(member);
    }

    public int size() {
        return members.size();
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }
}
