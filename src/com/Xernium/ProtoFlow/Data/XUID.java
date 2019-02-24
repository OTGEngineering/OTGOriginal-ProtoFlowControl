package com.Xernium.ProtoFlow.Data;

import java.math.BigInteger;

public class XUID {

	private BigInteger xuid = null;
	private XUID(BigInteger numid ) {
		xuid = numid;
	}
	
	public String getID() {
		return xuid.toString();
	}
	
	public static XUID fromString(String s) {
		if(s == null)
			return null;
		BigInteger r = null;
		try {
			r = new BigInteger(s);
		} catch(Exception e) {
			return null;
		}
		return new XUID(r);
		
		
	}
	
}
