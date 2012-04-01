package com.fuckingwin.bukkit.rakiru.slap;

import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Sean Gordon (rakiru)
 */
public class SlapConfig {

	private SlapPlugin plugin;
	private FileConfiguration config;
	private String msg[] = new String[11];
	private boolean isDebug;

	public SlapConfig(SlapPlugin plugin) {
		this.plugin = plugin;
	}

	public void load() {
		plugin.reloadConfig();
		config = plugin.getConfig();
		config.addDefault("debug", false);
		config.addDefault("messages.0", "{SLAPPER} touched {PLAYER}'s face");
		config.addDefault("messages.1", "{SLAPPER} slapped {PLAYER}!");
		config.addDefault("messages.4", "{SLAPPER} slapped {PLAYER} hard!");
		config.addDefault("messages.7", "{SLAPPER} slapped {PLAYER} and left a mark!");
		config.addDefault("messages.10", "{SLAPPER} bitchslapped {PLAYER}!");
		config.options().copyDefaults(true);
		loadMessages();
		isDebug = config.getBoolean("debug");
		plugin.saveConfig();
	}

	private void loadMessages() {
		Map<String, Object> messages = config.getConfigurationSection("messages").getValues(false);

		plugin.log.debug("First round");
		for (int i = 0; i < 11; i++) {
			String j = Integer.toString(i);
			if (messages.containsKey(j)) {
				plugin.log.debug("messages contains key " + i + " which has value of " + messages.get(j));
				msg[i] = (String)messages.get(j);
			} else if (i > 0) {
				msg[i] = msg[i - 1];
			}
			plugin.log.debug(msg[i]);
		}
		if (msg[10] == null) {
			plugin.log.debug("msg 10 is null");
			msg[10] = "{SLAPPER} slapped {PLAYER}!";
		}
		plugin.log.debug("Second round");
		for (int i = 9; i > -1; i--) {
			if (msg[i] == null) {
				msg[i] = msg[i + 1];
			}
			plugin.log.debug(msg[i]);
		}
	}

	public String getMessage(float power, String slapper, String slappee) {
		return msg[(int)power].replace("{SLAPPER}", slapper).replace("{PLAYER}", slappee);
	}

	public boolean isDebug() {
		return isDebug;
	}
}
