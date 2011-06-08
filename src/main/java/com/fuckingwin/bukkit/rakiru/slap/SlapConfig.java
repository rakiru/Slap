// Thanks to Yetanotherx for help getting started
package com.fuckingwin.bukkit.rakiru.slap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Set;

import org.bukkit.util.config.Configuration;

public class SlapConfig {

    /**
     * Settings
     */
    public static HashMap<Integer, String> messages = new HashMap<Integer, String>();
    public static String msg[] = new String[11];
    public static boolean debugMode = false;
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
            config.setProperty("debug", debugMode);
            config.save();
        }

        setSettings();

    }

    /**
     * Sets the internal variables
     */
    private static void setSettings() {

        debugMode = config.getBoolean("debug", false);
        messages = (HashMap<Integer, String>)config.getProperty("messages");

        SlapPlugin.log.debug(messages.keySet().toString());
        
        SlapPlugin.log.debug("First round");
        for (int i = 0; i < 11; i++) {
            if (messages.containsKey(i)) {
                SlapPlugin.log.debug("messages contains key " + i + " which has value of " + messages.get(i));
                msg[i] = messages.get(i);
            } else if (i>0) {
                msg[i] = msg[i-1];
            }
            SlapPlugin.log.debug(msg[i]);
        }
        if (msg[10] == null) {
            SlapPlugin.log.debug("msg 10 is null");
            msg[10] = "{SLAPPER} slapped {PLAYER}!";
        }
        SlapPlugin.log.debug("Second round");
        for (int i = 9; i > -1; i--) {
            if (msg[i] == null) {
                msg[i] = msg[i+1];
            }
            SlapPlugin.log.debug(msg[i]);
        }

//        List<String> keys = config.getKeys("messages");
//            for (String key : keys) {
//		try {
//			int keyInt = Integer.parseInt(key);
//                	messages.put(keyInt, config.getString("messages." + key));
//		} catch (NumberFormatException nfe) {
//			nfe.printStackTrace();
//			// Do whatever if you want to do on a fucked up config here
//		}
//            }
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
