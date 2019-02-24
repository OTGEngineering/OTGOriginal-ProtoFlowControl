package com.Xernium.ProtoFlow.Commands;

import java.util.ArrayList;
import java.util.List;

import com.Xernium.ProtoFlow.Data.CommandArgType;

public class WhitelistRemoveXUID_BigInt extends WhitelistRemoveXUID {


	@Override
	public List<CommandArgType> getArgs() {
		List<CommandArgType> reti = new ArrayList<CommandArgType>();
		reti.add(CommandArgType.BIG_INT);
		return reti;
	}

	@Override
	public boolean showInHelp() {
		return false;
	}


}
