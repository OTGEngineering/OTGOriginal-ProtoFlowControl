package com.Xernium.ProtoFlow;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.Xernium.ProtoFlow.Data.CommandArgType;


import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class Tools {

	public static void disableOld() {

		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_4_7);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_5_1);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_5_2);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_6_1);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_6_2);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_6_4);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_7_5);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_7_10);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_8);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_9);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_9_1);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_9_2);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_9_4);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_10);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_11);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_11_1);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_12);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_12_1);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_12_2);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_13);
		ProtocolSupportAPI.disableProtocolVersion(ProtocolVersion.MINECRAFT_1_13_1);

	}

	public static Set<Player> getOnlinePlayersBukkit() {
		Set<Player> reti = new HashSet<Player>();
		for (Player p : Bukkit.getOnlinePlayers())
			if (p != null)
				reti.add(p);

		return reti;
	}

	public static final boolean debug = false;

	public static void bug(String... t) {
		if (debug)
			for (int i = 0; i < t.length; i++) {
				if (t[i] != null) {
					System.out.println("[ProtoFlow-Debug] " + t[i]);
				}
			}
	}
	
	public static void broadcastAdmin(String message) {
		if(message == null)
			return;
		Set<Player> r = getOnlinePlayersBukkit();
		for(Player p:r) {
			if(checkPermission("protoflow.admin",p)) {
				p.sendMessage("§7[§bProto§cFlow§7] §f" + message);
			}
				
		}
		System.out.println("[ProtoFlow] " + ChatColor.stripColor(message));
	}
	
	public static boolean checkPerm(String node, CommandSender s) {
		if (s.hasPermission(node) || s.isOp())
			return true;
		Set<String> perms = new HashSet<String>();
		for (PermissionAttachmentInfo m : s.getEffectivePermissions()) {
			if (m.getValue() && m.getPermission() != null && !m.getPermission().equals(""))
				perms.add(m.getPermission().toLowerCase());
		}
		String[] split = node.split("\\.");
		for (int i = split.length - 1; i > 0; i--) {
			String t = "";
			for (int j = 0; j < i; j++) {
				if (t != "") {
					t = t + "." + split[j];
				} else {
					t = split[j];
				}
			}
			if (s.hasPermission(t) || perms.contains(t.toLowerCase()))
				return true;
		}

		return false;
	}
	
	public static boolean checkPermission(String node, Player p) {
		if (p.hasPermission(node) || p.isOp())
			return true;
		Set<String> perms = new HashSet<String>();
		for (PermissionAttachmentInfo m : p.getEffectivePermissions()) {
			if (m.getValue() && m.getPermission() != null && !m.getPermission().equals(""))
				perms.add(m.getPermission().toLowerCase());
		}
		String[] split = node.split("\\.");
		for (int i = split.length - 1; i > 0; i--) {
			String t = "";
			for (int j = 0; j < i; j++) {
				if (t != "") {
					t = t + "." + split[j];
				} else {
					t = split[j];
				}
			}
			if (p.hasPermission(t) || perms.contains(t.toLowerCase()))
				return true;
		}

		return false;
	}

	
	public static void sendErrorMessage(Player p, String message) {
		String pluginPrefix = ChatColor.GRAY + "[" + PluginMain.getPlugin().getPluginName() + ChatColor.GRAY + "]"
				+ ChatColor.RED + " Error!" + ChatColor.WHITE + " ";
		p.sendMessage(pluginPrefix + message);
	}

	public static void sendErrorMessage(CommandSender s, String message) {
		String pluginPrefix = ChatColor.GRAY + "[" + PluginMain.getPlugin().getPluginName() + ChatColor.GRAY + "]"
				+ ChatColor.RED + " Error!" + ChatColor.WHITE + " ";
		s.sendMessage(pluginPrefix + message);
	}

	public static void sendInfoMessage(Player p, String message) {
		String pluginPrefix = ChatColor.GRAY + "[" + PluginMain.getPlugin().getPluginName() + ChatColor.GRAY + "]"
				+ ChatColor.WHITE + " ";
		p.sendMessage(pluginPrefix + message);
	}

	public static void sendAdminMessage(Player p, String message) {
		String pluginPrefix = ChatColor.GRAY + "[" + PluginMain.getPlugin().getPluginName() + ChatColor.GRAY + " - "
				+ ChatColor.RED + "Admin" + ChatColor.GRAY + "]" + ChatColor.WHITE + " ";
		p.sendMessage(pluginPrefix + message);
	}

	public static void sendAdminMessage(CommandSender s, String message) {
		String pluginPrefix = ChatColor.GRAY + "[" + PluginMain.getPlugin().getPluginName() + ChatColor.GRAY + " - "
				+ ChatColor.RED + "Admin" + ChatColor.GRAY + "]" + ChatColor.WHITE + " ";
		s.sendMessage(pluginPrefix + message);
	}

	public static void sendInfoMessage(CommandSender s, String message) {
		String pluginPrefix = ChatColor.GRAY + "[" + PluginMain.getPlugin().getPluginName() + ChatColor.GRAY + "]"
				+ ChatColor.WHITE + " ";
		s.sendMessage(pluginPrefix + message);
	}
	
	
	public static Object getFromType(CommandArgType t, String s) {
		if (t.equals(CommandArgType.STRING_OR_NAME))
			return s;
		if (t.equals(CommandArgType.INT))
			return (int) Integer.parseInt(s);
		if (t.equals(CommandArgType.DOUBLE))
			return (double) Double.parseDouble(s);
		if(t.equals(CommandArgType.BIG_INT))
			return (BigInteger) new BigInteger(s);

		return null;
	}
	
	
	public static CommandArgType getType(String s) {
		try {
			@SuppressWarnings("unused")
			int a = Integer.parseInt(s);
			return CommandArgType.INT;
		} catch (Exception e) {
			try {
				BigInteger a = new BigInteger(s);
				if(a != null)
					return CommandArgType.BIG_INT;
			} catch (Exception f) {
			}
		}
		try {
			@SuppressWarnings("unused")
			double b = Double.parseDouble(s);
			return CommandArgType.DOUBLE;
		} catch (Exception e) {
		}
		

		return CommandArgType.STRING_OR_NAME;
	}

	public static boolean isBedrock(String name) {
		if(name == null)
			return false;
		Set<Player> r =  getOnlinePlayersBukkit();
		for(Player p : r) 
			if(p.getName().equalsIgnoreCase(name))
				return false;
		
		
		return true;
	}

}
