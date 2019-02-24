package com.Xernium.ProtoFlow;

import java.io.File;
import java.lang.reflect.Method;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.vagdedes.spartan.Register;

public class SpartanProvider {

	private Plugin spartan = null;
	private File spartanfile;

	public SpartanProvider(Plugin pl) {
		this.spartan = pl;
		try {
			Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
			getFileMethod.setAccessible(true);
			File file = (File) getFileMethod.invoke(pl);
			this.spartanfile = file;
		} catch (Exception e) {
			spartanfile = null;
		}

	}

	public Register getSpartan() {
		return (Register) spartan;
	}

	public boolean isSpartanConfigOK() {
		try {
			return spartan.getConfig().getBoolean("developer_api_events");
		} catch (Exception e) {
			return false;
		}

	}

	
	public boolean attemptSpartanConfigSetTrue() {
		try {
			spartan.getConfig().set("developer_api_events", true);
			spartan.saveConfig();
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public boolean shutdownSpartan() {
		try {

			spartan.getPluginLoader().disablePlugin(spartan);
		} catch (Exception e) {
			return false;
		}
		spartan = null;
		return true;

	}

	public boolean bootSpartan() {
		if (spartan != null || spartanfile == null)
			return false;
		try {
			Plugin p = PluginMain.getPlugin().getPluginLoader().loadPlugin(spartanfile);
			if (p == null)
				return false;
			spartan = p;
		} catch (Exception e) {
			return false;
		}
		return true;

	}

}
