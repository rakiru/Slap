package com.fuckingwin.bukkit.rakiru.slap;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Handler for the /slap command.
 * @author rakiru
 */
public class SlapCommand implements CommandExecutor {

	private final SlapPlugin plugin;
	private final SlapMethods methods;

	public SlapCommand(SlapPlugin plugin) {
		this.plugin = plugin;
		this.methods = new SlapMethods(plugin);
	}

	@Override
	public boolean onCommand(CommandSender slapper, Command command, String label, String[] split) {
		//Get the slapper's name for the message
		String slapperName;
		if (slapper instanceof Player) {
			Player slapperCast = (Player)slapper;
			slapperName = slapperCast.getDisplayName();
		} else if (slapper instanceof ConsoleCommandSender) {
			slapperName = "Console";
		} else {
			slapperName = "Someone";
		}

		//Check that we've got the right parameter count
		if (split.length == 1 || split.length == 2) {
			//Get the player that matches the given name
			List<Player> matchedPlayers = plugin.getServer().matchPlayer(split[0]);
			String slappeeName = split[0];
			//We only want one player, so make sure the name didn't match multiple players
			if (matchedPlayers.size() == 1) {
				//player found with no force argument
				//Get the full slappee name
				slappeeName = matchedPlayers.get(0).getDisplayName();
				//Set the force
				float force;
				if (split.length == 1) {
					//No force given, so use the default
					force = 2;
				} else {
					//Force given, attempt to parse it
					force = methods.getForce(split[1]);
					if (force == -1) {
						slapper.sendMessage("Force must be a number");
						return true;
					}
				}
				//Slap the player and broadcast a message
				methods.slapPlayer(matchedPlayers.get(0), force, slapperName, true);
			} else if (matchedPlayers.isEmpty()) {
				//player not found
				slapper.sendMessage("Player " + slappeeName + " not found");
			} else if (matchedPlayers.size() > 1) {
				//Multiple matching players found
				slapper.sendMessage(slappeeName + " matches multiple players");
			} else {
				slapper.sendMessage("Ah, bugger...");
			}
			return true;
		} else {
			//Show the usage from plugin.yml
			return false;
		}
	}
}
