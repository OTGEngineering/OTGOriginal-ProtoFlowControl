package com.Xernium.ProtoFlow;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


import me.vagdedes.spartan.api.PlayerViolationEvent;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;




public class SpartanEventListener implements Listener {
	

	public SpartanEventListener(PluginMain plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void vViolation(PlayerViolationEvent e) {
		Connection c = ProtocolSupportAPI.getConnection(e.getPlayer());
		if(c.getVersion().toString().contains("_PE_")) {
			e.setCancelled(true);
			
		}
		
		
	}

	

}
