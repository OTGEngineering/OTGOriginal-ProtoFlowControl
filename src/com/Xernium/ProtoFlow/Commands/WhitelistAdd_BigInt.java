package com.Xernium.ProtoFlow.Commands;

import java.util.ArrayList;
import java.util.List;

import com.Xernium.ProtoFlow.Data.CommandArgType;


public class WhitelistAdd_BigInt extends WhitelistAdd {

	@Override
	public boolean showInHelp() {
		return false;
	}

	

	@Override
	public List<CommandArgType> getArgs() {
		List<CommandArgType> reti = new ArrayList<CommandArgType>();
		reti.add(CommandArgType.STRING_OR_NAME);
		reti.add(CommandArgType.BIG_INT);
		return reti;
	}

	
	

}
