package com.Xernium.ProtoFlow;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.Xernium.ProtoFlow.Data.CommandArgType;
import com.Xernium.ProtoFlow.Data.PFCommandable;


public class CommandManager {

	private static CommandManager _this = null;

	private Set<PFCommandable> _commands = new HashSet<PFCommandable>();
	

	private CommandManager(Set<PFCommandable> _c) {
		_commands = _c;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// So good so far?
		// Help?
		if (args.length <= 0 || args[0].equalsIgnoreCase("help")) {
			int page = 1;
			if (args.length > 1) {
				if (args[1] != null) {
					try {
						page = Integer.parseInt(args[1]);
					} catch (Exception e) {
						page = 1;
					}
				}
			}
			printHelp(sender, page);
			return false;
		}
		String command = args[0];
		// System.out.println("Command! : " + command);
		List<CommandArgType> argT = new ArrayList<CommandArgType>();
		List<Object> argV = new ArrayList<Object>();
		if (args.length > 1) {
			for (int i = 1; i < args.length; i++) {
				String arg = args[i];
				if (arg == null || arg.equals(""))
					continue;
				CommandArgType arg_t = Tools.getType(arg);
				Object arg_v = Tools.getFromType(arg_t, arg);
				argT.add(arg_t);
				argV.add(arg_v);
				// System.out.println("t: '" + arg_t.toString() + "' v: '" +
				// arg_v.toString() + "'");
			}
		}
		
			for (PFCommandable c : _commands) {
				if (c.getCommand().equalsIgnoreCase(command) && c.getArgs().equals(argT)
						&& Tools.checkPerm(PluginMain.getPlugin().getPluginNameRaw().toLowerCase() + ".command."
								+ c.getCommand().toLowerCase(), sender)) {
					c.handleCommand(sender, argV);
					return true;
				}
			}
			if (!printSingleHelp(sender, command))
				printHelp(sender, 1);
		
		
		return true;
	}

	private void printHelp(CommandSender s, int page) {
		Set<PFCommandable> accessible = new HashSet<PFCommandable>();
		

			for (PFCommandable c : _commands) {
				if (c.showInHelp() && Tools.checkPerm(PluginMain.getPlugin().getPluginNameRaw().toLowerCase() + ".command." + c.getCommand().toLowerCase(), s))
					accessible.add(c);

			}
		

		String prefix = "";
		
		
			if (accessible.isEmpty()) {
				if (_commands.isEmpty()) {
					Tools.sendErrorMessage(s, "Internal failure? There are NO commands registered? :O");
					return;
				}
				Tools.sendErrorMessage(s, "You don't have permission to use any Plugin-Commands");
				return;

			}
			Tools.sendInfoMessage(s, "Plugin Help - Plugin Commands:");
		prefix = "pf";
		

		PFCommandable[] hca = accessible.toArray(new PFCommandable[accessible.size()]);
		int pageCount = hca.length/4;
		if(((hca.length+0.0d)/4.0d)>(hca.length/4))
			pageCount++;
		if(page<=0)
			page = 1;
		if(page>pageCount)
			page=pageCount;
		// Sort em:
		for (int i = page*4-4;i<(page*4);i++) {
			if(i>=hca.length)
				break;
			PFCommandable c = hca[i];
			
			String r = ChatColor.WHITE + "/" + prefix + " " + ChatColor.DARK_AQUA + c.getCommand().toLowerCase()
					+ ChatColor.WHITE + " ";
			for (CommandArgType ct : c.getArgs()) {
				String h = "<" + ct.toString().replaceAll("_", " ").substring(0, 1)
						+ ct.toString().replaceAll("_", " ").substring(1, ct.toString().length()).toLowerCase() + "> ";
				r = r + h;

			}
			s.sendMessage(ChatColor.GRAY + "Command: " + ChatColor.WHITE + r);
			s.sendMessage(ChatColor.GRAY + "Description: " + c.getDescription());

		}
		s.sendMessage(ChatColor.GRAY + " ---------------- " + ChatColor.WHITE + "Page " + page + "/" + pageCount
				+ ChatColor.GRAY + " --------------- ");
		//s.sendMessage(ChatColor.GRAY + " ---------------------------------------- ");

	}

	private boolean printSingleHelp(CommandSender s, String command) {

		String prefix = "";
		Set<PFCommandable> assume = new HashSet<PFCommandable>();
		
		
			prefix = "/pf";
			for (PFCommandable c : _commands) {
				if (c.getCommand().equalsIgnoreCase(command)) {
					if (Tools.checkPerm(PluginMain.getPlugin().getPluginNameRaw().toLowerCase() + ".command."
							+ c.getCommand().toLowerCase(), s))
						assume.add(c);
				}
			}
		
		if (assume.isEmpty())
			return false;
		Tools.sendErrorMessage(s, "Can't find that command with these arguments! Did you mean to do:");
		for (PFCommandable c : assume) {
			String r = ChatColor.GRAY + " Correct usage: " + ChatColor.WHITE + prefix + " "
					+ c.getCommand().toLowerCase() + " ";
			for (CommandArgType ct : c.getArgs()) {
				String h = "<" + ct.toString().replaceAll("_", " ").substring(0, 1)
						+ ct.toString().replaceAll("_", " ").substring(1, ct.toString().length()).toLowerCase() + "> ";
				r = r + h;

			}
			s.sendMessage(r);
		}
		return true;

	}

	public static CommandManager getCommandSystem() {
		if (_this != null)
			return _this;
		Set<PFCommandable> newSet = loadModules();
		if (newSet == null || newSet.isEmpty())
			return null;
		_this = new CommandManager(newSet);
		return _this;
	}

	private static Set<PFCommandable> loadModules() {
		Set<PFCommandable> reti = new HashSet<PFCommandable>();
		ClassLoader cl = PluginMain.getPluginClassLoader();
		String pluginFilePath = PluginMain.getPlugin().getDataFolder().getAbsolutePath();

		String source = PluginMain.getPlugin().getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

		// This stupidity is for Windows....
		String seperator = "\\\\";
		if (source.contains("/"))
			seperator = "/";

		String[] sourceSplit = source.split(seperator);
		pluginFilePath = pluginFilePath.substring(0,
				pluginFilePath.length() - PluginMain.getPlugin().getName().length())
				+ sourceSplit[sourceSplit.length - 1].replace("%20", " ");

		// End stupidity
		try {
			JarFile jar = new JarFile(pluginFilePath);
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				String entry = entries.nextElement().getName();
				if (entry == null || entry.equals("") || !entry.endsWith(".class") || entry.contains("$"))
					continue;
				String[] split = entry.split(seperator);
				if (split.length > 5)
					continue;
				if (!split[0].equalsIgnoreCase("com") || !split[1].equalsIgnoreCase("Xernium")
						|| !split[2].equalsIgnoreCase("ProtoFlow") || !split[3].equalsIgnoreCase("Commands"))
					continue;
				entry = entry.replace(".class", "").replaceAll(seperator, ".");
				Object o = null;
				try {
					o = cl.loadClass(entry).getDeclaredConstructor().newInstance();
				} catch (ClassNotFoundException e) {
				}
				if (o != null && o instanceof PFCommandable)
					reti.add((PFCommandable) o);
			}

			jar.close();
		} catch (Exception e) {
		}
		return reti;
	}
}
