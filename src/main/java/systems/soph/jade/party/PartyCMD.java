package systems.soph.jade.party;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import systems.soph.jade.Jade;
import systems.soph.jade.Palette;

import java.util.List;

public class PartyCMD implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You cannot use this command from console.")
                    .color(Palette.ERROR));
            return true;
        }

        // No arguments
        if (args.length == 0) {
            Party party = PartyManager.getParty(player);
            if (party == null) {
                sender.sendMessage(Component.text("Usage: /party <create|invite|kick|leave|disband|list|promote>")
                        .color(Palette.ERROR));
            } else {
                memberList(player, party);
            }
            return false;
        }

        switch (args[0].toLowerCase()) {

            case "create" -> {
                if (PartyManager.getParty(player) != null) {
                    player.sendMessage(Component.text("You are already in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                PartyManager.createParty(player);
                player.sendMessage(Component.text("You have created a party! ")
                        .color(Palette.SUCCESS)
                        .append(Component.text("[Invite] ")
                                .color(Palette.ACCENT)
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party invite "))
                                .hoverEvent(Component.text("Click to invite a player to the party.").color(Palette.ALT)))
                        .append(Component.text("[Disband]")
                                .color(Palette.ERROR)
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/party disband"))
                                .hoverEvent(Component.text("Click to disband the party.").color(Palette.ALT))));
            }

            case "invite" -> {
                if (args.length < 2) {
                    player.sendMessage(Component.text("Usage: /party invite <player>")
                            .color(Palette.ERROR));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Component.text("Player not found.")
                            .color(Palette.ERROR));
                    return true;
                }
                Party party = PartyManager.getParty(player);
                if (party == null) {
                    player.sendMessage(Component.text("You are not in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (!party.isLeader(player)) {
                    player.sendMessage(Component.text("You are not the party leader.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (party.isMember(target)) {
                    player.sendMessage(Component.text("Player is already in the party.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (party.hasInvite(target)) {
                    player.sendMessage(Component.text("Player already has an invite.")
                            .color(Palette.ERROR));
                    return true;
                }
                party.addInvite(target);
                player.sendMessage(Component.text("You have invited " + target.getName() + " to the party!")
                        .color(Palette.SUCCESS));

                target.sendMessage(Component.text(player.getName() + " has sent you a party invite!")
                        .color(Palette.SUCCESS));
                target.sendMessage(Component.text("[Accept] ")
                        .color(Palette.ACCENT)
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/party join " + player.getName()))
                                .hoverEvent(Component.text("Click to accept the party invite!").color(Palette.ALT))
                        .append(Component.text("[Decline]")
                                .color(Palette.ERROR)
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/party decline " + player.getName()))
                                .hoverEvent(Component.text("Click to decline the party invite!").color(Palette.ALT))));

                // Expire invite after 40 seconds
                Bukkit.getScheduler().runTaskLater(Jade.getInstance(), () -> {
                    if (party.hasInvite(target)) {
                        party.removeInvite(target);
                        player.sendMessage(Component.text("Party invite to " + target.getName() + " has expired.")
                                .color(Palette.ERROR));
                        target.sendMessage(Component.text("Your party invite from " + player.getName() + " has expired.")
                                .color(Palette.ERROR));
                    }
                }, 20 * 40);

            }

            case "join" -> {
                if (args.length < 2) {
                    player.sendMessage(Component.text("Usage: /party join <player>")
                            .color(Palette.ERROR));
                    return true;
                }
                Player leader = Bukkit.getPlayer(args[1]);
                if (leader == null) {
                    player.sendMessage(Component.text("Player not found.")
                            .color(Palette.ERROR));
                    return true;
                }
                Party party = PartyManager.getParty(leader);
                if (party == null) {
                    player.sendMessage(Component.text("Player is not in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (!party.hasInvite(player)) {
                    player.sendMessage(Component.text("You do not have an invite to this party.")
                            .color(Palette.ERROR));
                    return true;
                }
                party.removeInvite(player);
                party.addMember(player);
                player.sendMessage(Component.text("You have joined " + leader.getName() + "'s party!")
                        .color(Palette.SUCCESS));
                party.message(Component.text(player.getName() + " has joined the party!").color(Palette.SUCCESS));
            }

            case "decline" -> {
                if (args.length < 2) {
                    player.sendMessage(Component.text("Usage: /party decline <player>")
                            .color(Palette.ERROR));
                    return true;
                }
                Player leader = Bukkit.getPlayer(args[1]);
                if (leader == null) {
                    player.sendMessage(Component.text("Player not found.")
                            .color(Palette.ERROR));
                    return true;
                }
                Party party = PartyManager.getParty(leader);
                if (party == null) {
                    player.sendMessage(Component.text("Player is not in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (!party.hasInvite(player)) {
                    player.sendMessage(Component.text("You do not have an invite to this party.")
                            .color(Palette.ERROR));
                    return true;
                }
                party.removeInvite(player);
                player.sendMessage(Component.text("You have declined " + leader.getName() + "'s party invite.")
                        .color(Palette.ERROR));
            }

            case "kick" -> {
                if (args.length < 2) {
                    player.sendMessage(Component.text("Usage: /party kick <player>")
                            .color(Palette.ERROR));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Component.text("Player not found.")
                            .color(Palette.ERROR));
                    return true;
                }
                Party party = PartyManager.getParty(player);
                if (party == null) {
                    player.sendMessage(Component.text("You are not in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (!party.isLeader(player)) {
                    player.sendMessage(Component.text("You are not the party leader.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (!party.isMember(target)) {
                    player.sendMessage(Component.text("Player is not in the party.")
                            .color(Palette.ERROR));
                    return true;
                }
                party.removeMember(target);
                party.message(Component.text(target.getName() + " has been removed from the party.")
                        .color(Palette.ERROR));
                target.sendMessage(Component.text("You have been removed from " + player.getName() + "'s party.")
                        .color(Palette.ERROR));
            }

            case "leave" -> {
                Party party = PartyManager.getParty(player);
                if (party == null) {
                    player.sendMessage(Component.text("You are not in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (party.isLeader(player)) {
                    for (Player member : party.getMembers()) {
                        if (!member.equals(player)) {
                            party.setLeader(member);
                            break;
                        }
                    }
                }
                party.removeMember(player);
                party.message(Component.text(player.getName() + " has left the party.")
                        .color(Palette.ERROR));
                player.sendMessage(Component.text("You have left the party.")
                        .color(Palette.ERROR));
            }

            case "disband" -> {
                Party party = PartyManager.getParty(player);
                if (party == null) {
                    player.sendMessage(Component.text("You are not in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (!party.isLeader(player)) {
                    player.sendMessage(Component.text("You are not the party leader.")
                            .color(Palette.ERROR));
                    return true;
                }
                party.message(Component.text("The party has been disbanded.")
                        .color(Palette.ERROR));
                PartyManager.disbandParty(party);
                player.sendMessage(Component.text("You have disbanded the party.")
                        .color(Palette.ERROR));
            }

            case "list" -> {
                Party party = PartyManager.getParty(player);
                if (party == null) {
                    player.sendMessage(Component.text("You are not in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                memberList(player, party);
            }

            case "promote" -> {
                if (args.length < 2) {
                    player.sendMessage(Component.text("Usage: /party promote <player>")
                            .color(Palette.ERROR));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Component.text("Player not found.")
                            .color(Palette.ERROR));
                    return true;
                }
                Party party = PartyManager.getParty(player);
                if (party == null) {
                    player.sendMessage(Component.text("You are not in a party.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (!party.isLeader(player)) {
                    player.sendMessage(Component.text("You are not the party leader.")
                            .color(Palette.ERROR));
                    return true;
                }
                if (!party.isMember(target)) {
                    player.sendMessage(Component.text("Player is not in the party.")
                            .color(Palette.ERROR));
                    return true;
                }
                party.setLeader(target);
                party.message(Component.text(target.getName() + " has been promoted to party leader.")
                        .color(Palette.SUCCESS));
            }

        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 1) {
            return List.of(
                    "create",
                    "invite",
                    "join",
                    "decline",
                    "kick",
                    "leave",
                    "disband",
                    "list",
                    "promote"
            );
        }

        return null;
    }

    private void memberList(Player player, Party party) {
        player.sendMessage(Component.text("Party Leader: ")
                .color(Palette.TEXT)
                .append(Component.text(party.getLeader().getName())
                        .color(Palette.ACCENT)));

        StringBuilder members = new StringBuilder();
        for (Player member : party.getMembers()) {
            members.append(member.getName());
            if (party.getMembers().indexOf(member) != party.size() - 1) {
                members.append(", ");
            }
        }
        player.sendMessage(Component.text("Members: ")
                .color(Palette.TEXT)
                .append(Component.text(members.toString())
                        .color(Palette.ACCENT)));
    }

}
