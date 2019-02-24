package com.Xernium.ProtoFlow;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.Xernium.ProtoFlow.Data.BedrockPlayer;
import com.Xernium.ProtoFlow.Data.XUID;

import protocolsupport.api.events.PlayerLoginStartEvent;




public class PluginEventListener implements Listener {
	
	private BedrockPlayer lastPlayer = null;

	public PluginEventListener(PluginMain plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public BedrockPlayer getLastPlayer() {
		return lastPlayer;
	}
	
	public void resetLastPlayer() {
		lastPlayer = null;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void vLogin(PlayerLoginStartEvent e) {
		if(e.getConnection().getVersion().toString().contains("_PE_")) {
			//XUID uuid = XUID.fromString();
			String t =  (String)e.getConnection().getMetadata("___PS_PE_XUID") + "";
			XUID uid = XUID.fromString(t);
			String un = e.getConnection().getProfile().getName();
			Tools.bug("PE Connection: '" + uid.getID() + "' name: '" + un + "'");
			BedrockPlayer p = Database.get(uid);
			if(p == null) {
				e.denyLogin("§7[§bProto§cFlow§7] §fThis Profile is not authorized to join the Server. To get access please have a Staff-Member whitelist you! You will need to provide your username: '§7" + un + "§f' and your XUID: §7" + t);
				Tools.broadcastAdmin("Player §d" + un + "§f would like to use Minecraft Bedrock Edition to join the Server. To allow this either do '§7/pf whitelistAllowLastNew§f' or '§7/pf whitelistadd " + un + " " + uid.getID() + "§f'" );
				lastPlayer = BedrockPlayer.assumeNew(un, uid);
				return;
			} else {
				Tools.bug("Connection contains PE name " + un + " p authorized");
				
			}
			
			
			
		}
		
	}

	

}
