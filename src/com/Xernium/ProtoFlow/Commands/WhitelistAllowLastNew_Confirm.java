package com.Xernium.ProtoFlow.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.Xernium.ProtoFlow.Database;
import com.Xernium.ProtoFlow.PluginMain;
import com.Xernium.ProtoFlow.Tools;
import com.Xernium.ProtoFlow.Data.BedrockPlayer;
import com.Xernium.ProtoFlow.Data.CommandArgType;
import com.Xernium.ProtoFlow.Data.PFCommandable;



public class WhitelistAllowLastNew_Confirm extends PFCommandable{

	
	@Override
	public boolean showInHelp(){
		return false;
	}
	
	@Override
	public void handleCommand(CommandSender s, List<Object> args) {
		String c = (String)args.get(0);
		if(c.equalsIgnoreCase("confirm")) {
			BedrockPlayer lp = PluginMain.getPlugin().getPluginEventListener().getLastPlayer();
			if(lp == null) {
				Tools.sendErrorMessage(s, "No last player exists!");
				return;
			}
			if(Database.createNewDatabaseEntry(lp)) {
				Tools.sendInfoMessage(s, "Player " + ChatColor.LIGHT_PURPLE + lp.getName() + ChatColor.WHITE + " with XBox-Live UUID " + ChatColor.GRAY + lp.getXUID().getID() + ChatColor.WHITE + " has been whitelisted successfully and may now join!");
			} else {
				Tools.sendErrorMessage(s, "An Unknown Error occured, please check the Console for more information!");
			}
			PluginMain.getPlugin().getPluginEventListener().resetLastPlayer();
			
		} else {
			Tools.sendErrorMessage(s, "You can confirm with /pf whitelistAllowLastNew" + ChatColor.LIGHT_PURPLE + "confirm" + ChatColor.WHITE + ", if you wish to cancel instead just do nothing!");
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
		return "whitelistAllowLastNew";
	}

	@Override
	public String getDescription() {
		return "Whitelists the newest Bedrock User";
	}

}
