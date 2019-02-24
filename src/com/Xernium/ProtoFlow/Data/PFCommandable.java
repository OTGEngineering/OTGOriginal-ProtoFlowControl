package com.Xernium.ProtoFlow.Data;

import java.util.List;

import org.bukkit.command.CommandSender;


public abstract class PFCommandable {

	public abstract List<CommandArgType> getArgs();
	
	public abstract String getCommand();
	
	public boolean showInHelp(){
		return true;
	}
	public abstract String getDescription();
	
	public void handleCommand(CommandSender s, List<Object> argV) {
		
	}
}
