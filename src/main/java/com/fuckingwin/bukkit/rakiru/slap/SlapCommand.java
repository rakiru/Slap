package com.fuckingwin.bukkit.rakiru.slap;

import java.util.List;
import java.util.Random;
import java.lang.Math;
import org.bukkit.util.Vector;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

/**
 * Handler for the /slap command.
 * @author rakiru
 */
public class SlapCommand implements CommandExecutor {

    private final SlapPlugin plugin;

    public SlapCommand(SlapPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender slapper, Command command, String label, String[] split) {
        if (plugin.usePermissions) {
            if (!(plugin.permissionHandler.has((Player) slapper, "slap.slap"))) {
                slapper.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            }
        } else if (!(slapper.isOp())) {
            slapper.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        String slapperName;
        if (slapper instanceof Player) {
            Player slapperCast = (Player) slapper;
            slapperName = slapperCast.getDisplayName();
        } else if (slapper instanceof ConsoleCommandSender) {
            slapperName = "Console";
        } else {
            slapperName = "Someone";
        }
        if (split.length == 1 || split.length == 2) {
            List<Player> matchedPlayers = plugin.getServer().matchPlayer(split[0]);
            String slappeeName = split[0];
            if (matchedPlayers.size() == 1) {
                //player found with no force argument
                if (split.length == 1) {
                    slapPlayer(matchedPlayers.get(0), 2, slapperName);
                } else {
                    float force;
                    try {
                        force = Float.parseFloat(split[1]);
                    } catch (NumberFormatException e) {
                        slapper.sendMessage("Force must be a number");
                        return true;
                    }
                    if (force > 10) {
                        force = 10;
                    } else if (force <= 0) {
                        force = 0.1f;
                    }
                    slapPlayer(matchedPlayers.get(0), force, slapperName);
                }
            } else if (matchedPlayers.isEmpty()) {
                //player not found
                slapper.sendMessage("Player " + slappeeName + " not found");
            } else if (matchedPlayers.size() > 1) {
                //Multiple matching players found
                slapper.sendMessage(slappeeName + " matches multiple players");
            } else {
                //Unknown error
                slapper.sendMessage("Ah, crap!");
            }
            return true;
        } else {
            return false;
        }
    }

    private void slapPlayer(Player slappee, float force, String slapperName) {
        Random randomGen = new Random();
        Vector newVelocity = new Vector((randomGen.nextFloat() * 1.5 - 0.75) * force, randomGen.nextFloat() / 2.5 + (0.4 * force), (randomGen.nextFloat() * 1.5 - 0.75) * force);
        slappee.setVelocity(newVelocity);
        String slappeeName = slappee.getDisplayName();
        // Change message depending on the force
        String prefix = "slapped";
        String suffix = "";
        if (force == 10) {
            prefix = "bitchslapped";
        } else if (force >= 7) {
            suffix = " and left a mark";
        } else if (force >= 4) {
            suffix = " hard";
        } else if (force < 1) {
            prefix = "touched";
            suffix = "'s face";
        }
        plugin.getServer().broadcastMessage(slapperName + " " + prefix + " " + slappeeName + suffix + "!");
    }
}
