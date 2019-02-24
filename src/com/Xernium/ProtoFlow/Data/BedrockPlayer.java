package com.Xernium.ProtoFlow.Data;

public class BedrockPlayer {

	
	public static BedrockPlayer get() {
		
		
		return null;
	}
	
	
	public static BedrockPlayer assumeNew(String name, XUID uuid) {
		if(uuid == null || name == null)
			return null;
		return new BedrockPlayer(uuid,name);

	}
	
	private BedrockPlayer(XUID olxuid, String name) {
		_name = name;
		_xuid = olxuid;
	}
	private XUID _xuid = null;
	private String _name = null;
	
	public String getName() {
		return _name;
	}
	
	public XUID getXUID() {
		return _xuid;
	}
}
