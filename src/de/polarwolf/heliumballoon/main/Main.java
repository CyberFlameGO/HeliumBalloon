package de.polarwolf.heliumballoon.main;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import de.polarwolf.heliumballoon.api.HeliumBalloonOrchestrator;
import de.polarwolf.heliumballoon.bstats.Metrics;
import de.polarwolf.heliumballoon.commands.BalloonCommand;
import de.polarwolf.heliumballoon.commands.BalloonTabCompleter;
import de.polarwolf.heliumballoon.config.ConfigManager;
import de.polarwolf.heliumballoon.exception.BalloonException;

public final class Main extends JavaPlugin { 
	
	protected HeliumBalloonOrchestrator orchestrator = null;
	protected BalloonCommand balloonCommand = null;
	protected BalloonTabCompleter balloonTabCompleter = null;

	@Override
	public void onEnable() {
		
		// Prepare Configuration
		saveDefaultConfig();
		
		// Register commands		
		balloonCommand = new BalloonCommand(this);
		getCommand("balloon").setExecutor(balloonCommand);
		balloonTabCompleter = new BalloonTabCompleter(balloonCommand);
		getCommand("balloon").setTabCompleter(balloonTabCompleter);
		
		// Enable bStats Metrics
		// Please download the bstats-code direct form their homepage
		// or disable the following instruction
		new Metrics(this, Metrics.PLUGINID_HELIUMBALLOON);

		// Check for passive mode
		if (ConfigManager.isPassiveMode(this)) {
			getLogger().info("HeliumBalloon is in passive mode. You must register your own orchestrator.");
			return;
		}
		
		// Startup Orchestrator
		orchestrator = new HeliumBalloonOrchestrator(this);
		orchestrator.registerAPI();
								
		// Load Configuration Section
		try {
			orchestrator.getConfigManager().reload();
		} catch (BalloonException e) {
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "ERROR " + e.getMessage());
			getLogger().warning("Error loading config");
		}
		
		// Initialize Walls
		orchestrator.getWallManager().reload();
	}

	
	@Override
	public void onDisable() {
		if (orchestrator != null) {
			orchestrator.disable();
			getLogger().info("All pets and balloons are removed");
		}
	}

}
