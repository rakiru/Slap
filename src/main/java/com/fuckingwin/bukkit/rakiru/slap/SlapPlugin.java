package com.fuckingwin.bukkit.rakiru.slap;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Slap plugin for bukkit
 *
 * @author rakiru
 */
public class SlapPlugin extends JavaPlugin {

    public SlapLogger log;
	public SlapConfig config;

	@Override
    public void onDisable() {
        // Output to console that plugin is disabled
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled!");
    }

	@Override
    public void onEnable() {
		// Set up logger
		log = new SlapLogger(this);

        // Load config
        config = new SlapConfig(this);
		config.load();
        
        // Get plugin info from plugin.yml
        PluginDescriptionFile pdfFile = this.getDescription();

        // Register commands
        getCommand("slap").setExecutor(new SlapCommand(this));
        getCommand("megaslap").setExecutor(new MegaslapCommand(this));

        // Output to console that plugin is enabled
        log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled!");
    }
}
