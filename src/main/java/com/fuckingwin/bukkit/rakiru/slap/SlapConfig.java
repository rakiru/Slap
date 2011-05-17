// Thanks to Yetanotherx for help getting started
package com.fuckingwin.bukkit.rakiru.slap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;

import org.bukkit.util.config.Configuration;

public class SlapConfig {
    
    /**
     * Settings
     */

    public static HashMap<Integer, String> messages = new HashMap<Integer, String>();

    /**
     * Bukkit config class
     */
    public static Configuration config = null;

    /**
     * Load and parse the YAML config file
     */
    public static void load() {

        File dataDirectory = new File("plugins" + File.separator + "Slap" + File.separator);

        dataDirectory.mkdirs();

        File file = new File("plugins" + File.separator + "Slap", "config.yml");

        config = new Configuration(file);
        config.load();

        if (!file.exists()) {
            config.setProperty("messages", getDefaultMessages());
            config.save();
        }

        setSettings();
    }

    /**
     * Sets the internal variables
     */
    private static void setSettings() {

        List<Integer> keys = config.getKeys("messages");

        if (keys != null) {
            for (Integer key : keys) {
                messages.put(key, config.getString("messages." + key));
            }
        }
    }

    private static HashMap<Integer, String> getDefaultMessages() {

        HashMap<Integer, String> defaultMessages = new HashMap<Integer, String>();

        defaultMessages.put(0, "{SLAPPER} touched {PLAYER}'s face");
        defaultMessages.put(1, "{SLAPPER} slapped {PLAYER}!");
        defaultMessages.put(4, "{SLAPPER} slapped {PLAYER} hard!");
        defaultMessages.put(7, "{SLAPPER} slapped {PLAYER} and left a mark!");
        defaultMessages.put(10, "{SLAPPER} bitchslapped {PLAYER}!");

        return defaultMessages;

    }
}