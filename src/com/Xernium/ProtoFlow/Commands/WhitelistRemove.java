package com.Xernium.ProtoFlow.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.Xernium.ProtoFlow.Database;
import com.Xernium.ProtoFlow.Tools;
import com.Xernium.ProtoFlow.Data.BedrockPlayer;
import com.Xernium.ProtoFlow.Data.CommandArgType;
import com.Xernium.ProtoFlow.Data.PFCommandable;

public class WhitelistRemove extends PFCommandable {

	@Override
	public void handleCommand(CommandSender s, List<Object> args) {
		
		String name = (String) args.get(0);
		
		
		BedrockPlayer lp = Database.get(name);
		if (lp == null) {
			Tools.sendErrorMessage(s, "Invalid Playername!");
			return;
		}
		if (Database.deleteDatabaseEntry(lp.getXUID())) {
			Tools.sendInfoMessage(s,
					"Player " + ChatColor.LIGHT_PURPLE + lp.getName() + ChatColor.WHITE + " with XBox-Live UUID "
							+ ChatColor.GRAY + lp.getXUID().getID() + ChatColor.WHITE
							+ " has been REMOVED from the whitelist!");
		} else {
			Tools.sendErrorMessage(s, "An Unknown Error occured, please check the Console for more information!");
		}

	}

	@Override
	public List<CommandArgType> getArgs() {
		List<CommandArgType> reti = new ArrayList<CommandArgType>();
		reti.add(CommandArgType.STRING_OR_NAME);
		return reti;
	}

	@Override
	public String getCommand() {
		return "whitelistRemove";
	}

	@Override
	public String getDescription() {
		return "Un-Whitelists a Bedrock User";
	}

}
