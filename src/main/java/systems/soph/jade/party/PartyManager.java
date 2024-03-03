package systems.soph.jade.party;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyManager {

    private static ArrayList<Party> parties = new ArrayList<>();

    public static Party getParty(Player player) {
        for (Party party : parties) {
            if (party.isMember(player)) {
                return party;
            }
        }
        return null;
    }

    public static void createParty(Player leader) {
        parties.add(new Party(leader));
    }

    public static void disbandParty(Party party) {
        try {
            for (Player member : party.getMembers()) {
                party.removeMember(member);
            }
        } catch (Exception e) {
            // this works perfectly fine but still throws an exception for some reason :shrug:
        }
        parties.remove(party);
    }

    public static List<Party> getParties() {
        return parties;
    }

}
