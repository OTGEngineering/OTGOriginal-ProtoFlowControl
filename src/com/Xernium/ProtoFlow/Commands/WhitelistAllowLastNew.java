package com.Xernium.ProtoFlow.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.Xernium.ProtoFlow.PluginMain;
import com.Xernium.ProtoFlow.Tools;
import com.Xernium.ProtoFlow.Data.BedrockPlayer;
import com.Xernium.ProtoFlow.Data.CommandArgType;
import com.Xernium.ProtoFlow.Data.PFCommandable;



public class WhitelistAllowLastNew extends PFCommandable{

	@Override
	public void handleCommand(CommandSender s, List<Object> args) {
		BedrockPlayer lp = PluginMain.getPlugin().getPluginEventListener().getLastPlayer();
		if(lp == null) {
			Tools.sendErrorMessage(s, "No last player exists!");
			return;
		}
		Tools.sendInfoMessage(s, "Player " + ChatColor.LIGHT_PURPLE + lp.getName() + ChatColor.WHITE + " with XBox-Live UUID " + ChatColor.GRAY + lp.getXUID().getID() + ChatColor.WHITE + " is going to be whitelisted. Please do '/lp whitelistAllowLastNew confirm' to confirm!");
	}

	@Override
	public List<CommandArgType> getArgs() {
		return new ArrayList<CommandArgType>();
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
