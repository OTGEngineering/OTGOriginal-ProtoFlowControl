package com.Xernium.ProtoFlow.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Xernium.ProtoFlow.Tools;
import com.Xernium.ProtoFlow.Data.CommandArgType;
import com.Xernium.ProtoFlow.Data.PFCommandable;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

public class PlayerInfo extends PFCommandable {

	@Override
	public void handleCommand(CommandSender s, List<Object> args) {
		
		String name = (String) args.get(0);
		@SuppressWarnings("deprecation")
		Player p = Bukkit.getPlayer(name);
		if(p == null) {
			Tools.sendErrorMessage(s, "There is no Player by that name online!");
			return;
		}
		Connection c = ProtocolSupportAPI.getConnection(p);
		if(c == null) {
			Tools.sendErrorMessage(s, "Player is not connected over ProtocolSupport!");
			return;
		}
		String m = "Player §d" + p.getName() + " §fis connected with Protocol: ";
		
		if(c.getVersion().toString().contains("_PE_")) {
			m = m+ "§9Minecraft Bedrock " + c.getVersion().toString().replace("MINECRAFT_PE_", "").replace("_", ".");
		} else {
			m = m+ "§eMinecraft Java " + c.getVersion().toString().replace("MINECRAFT_", "").replace("_", ".");
		}
		
		Tools.sendInfoMessage(s, m);
	}

	@Override
	public List<CommandArgType> getArgs() {
		List<CommandArgType> reti = new ArrayList<CommandArgType>();
		reti.add(CommandArgType.STRING_OR_NAME);
		return reti;
	}

	@Override
	public String getCommand() {
		return "playerInfo";
	}

	@Override
	public String getDescription() {
		return "Displays Information on a Player";
	}

}
