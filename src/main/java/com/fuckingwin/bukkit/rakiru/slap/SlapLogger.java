package com.fuckingwin.bukkit.rakiru.slap;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a debug method, and prefixes all log messages with [Slap]
 */
public class SlapLogger {

    public static final Logger logger = Logger.getLogger("Minecraft");

    public void debug( String s ) {
        if(SlapConfig.debugMode) {
	logger.log(Level.INFO, "[Slap DEBUG] " + s);
	}
    }

    public void info( String s ) {
	logger.log(Level.INFO, "[Slap] " + s);
    }

    public void severe( String s ) {
	logger.log(Level.SEVERE, "[Slap] " + s);
    }

    public void warning( String s ) {
	logger.log(Level.WARNING, "[Slap] " + s);
    }

}