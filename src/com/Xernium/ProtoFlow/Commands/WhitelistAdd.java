package com.Xernium.ProtoFlow.Commands;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.Xernium.ProtoFlow.Database;
import com.Xernium.ProtoFlow.Tools;
import com.Xernium.ProtoFlow.Data.BedrockPlayer;
import com.Xernium.ProtoFlow.Data.CommandArgType;
import com.Xernium.ProtoFlow.Data.PFCommandable;
import com.Xernium.ProtoFlow.Data.XUID;

public class WhitelistAdd extends PFCommandable {

	@Override
	public void handleCommand(CommandSender s, List<Object> args) {
		
		String name = (String) args.get(0);
		
		Object t =  args.get(1);
		String w = null;
		if(t instanceof BigInteger) {
			w = ((BigInteger) t).toString();
		} else {
			w = ((int) t) + "";
		}
		
		XUID puid = XUID.fromString(w);
		
		BedrockPlayer lp = BedrockPlayer.assumeNew(name, puid);
		if (lp == null) {
			Tools.sendErrorMessage(s, "Invalid Player!");
			return;
		}
		if (Database.createNewDatabaseEntry(lp)) {
			Tools.sendInfoMessage(s,
					"Player " + ChatColor.LIGHT_PURPLE + lp.getName() + ChatColor.WHITE + " with XBox-Live UUID "
							+ ChatColor.GRAY + lp.getXUID().getID() + ChatColor.WHITE
							+ " has been whitelisted successfully and may now join!");
		} else {
			Tools.sendErrorMessage(s, "An Unknown Error occured, please check the Console for more information!");
		}

	}

	@Override
	public List<CommandArgType> getArgs() {
		List<CommandArgType> reti = new ArrayList<CommandArgType>();
		reti.add(CommandArgType.STRING_OR_NAME);
		reti.add(CommandArgType.INT);
		return reti;
	}

	@Override
	public String getCommand() {
		return "whitelistAdd";
	}

	@Override
	public String getDescription() {
		return "Whitelists a Bedrock User";
	}

}
