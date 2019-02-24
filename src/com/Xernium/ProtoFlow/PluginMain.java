package com.Xernium.ProtoFlow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class PluginMain extends JavaPlugin {

	public String getPluginName() {
		return ChatColor.AQUA + "Proto" + ChatColor.RED + "Flow";
	}

	public String getPluginNameRaw() {
		return this.getDescription().getName();
	}

	private static PluginMain _instance;

	public static PluginMain getPlugin() {
		return _instance;
	}

	public static ClassLoader getPluginClassLoader() {
		return _instance.getClassLoader();
	}

	public PluginEventListener getPluginEventListener() {
		return active;
	}

	private CommandManager _command = null;
	private PluginEventListener active = null;

	private SpartanProvider prov = null;
	
	public SpartanProvider getSpartan() {
		return prov;
	}

	public void onEnable() {
		Logger logger = (Logger) LogManager.getRootLogger();
		logger.addFilter(new MovementErrorFilter());
		_instance = this;
		getLogger().info("Checking Server environment...");
		Plugin pS = null;
		try {
			Plugin[] reg = Bukkit.getPluginManager().getPlugins();
			for (int i = 0; i < reg.length; i++) {
				if (reg[i] != null && reg[i].getName().equalsIgnoreCase("protocolsupport")) {
					pS = reg[i];
					break;
				}
			}
			if (pS == null)
				throw new Exception();
			if (!Database.attemptDatabaseConnect())
				throw new Exception();
		} catch (Exception e) {
			getLogger().info("Plugin can't work on this Server!");
			getPluginLoader().disablePlugin(this);
			return;
		}

		try {
			for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
				if (p.getName().contains("Spartan")) {
					prov = new SpartanProvider(p);
				}
			}
		} catch (Exception irrelovant) {
		}
		getLogger().info("Hooking into Spartan...");
		if (prov != null) {
			getLogger().info("Found Spartan!");
			if (!prov.isSpartanConfigOK()) {
				getLogger().info("Spartan Events are NOT ENABLED! Trying to adjust...");
				if (prov.attemptSpartanConfigSetTrue()) {
					getLogger().info("Successful! Continuing! :D");

				} else {
					getLogger().info("[ERROR] Could not adjust config!");
					getLogger().info("[ERROR] Set developer_api_events: true");
					getLogger().info("[ERROR] In the Spartan Config manually!");
					getLogger().info("Error loading Plugin! Spartan could not be hooked, aborting!");
					this.getPluginLoader().disablePlugin(this);
					return;
				}
			} else {
				getLogger().info("Spartan Events are enabled, continuing...");
			}
			new SpartanEventListener(this);
		} else {
			getLogger().info("Spartan is not present, skipping...");
		}
		getLogger().info("Loaded");

		_command = CommandManager.getCommandSystem();
		Tools.disableOld();
		active = new PluginEventListener(this);

	}

	public void onDisable() {
		getLogger().info("Plugin Shutting down...");
		getLogger().info("Stopping Tasks...");
		getLogger().info("Shutdown done. Unregistering...");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return _command.onCommand(sender, cmd, label, args);
	}

}
