# ProtoFlowControl
## A toast to https://github.com/ProtocolSupport/ProtocolSupport/tree/mcpenew you guys rock
This is a simple small Bukkit/Spigot Plugin to manage whitelisting 'Bedrock-Players' via ProtocolSupport-MCPE. This only caters to said Players

#### This plugin was made with support for Bedrock and Spartan Anticheat in mind   

Warning! This also currently disables all other features of ProtocolSupport (Will be updated in the future)

### Usage:

While this Plugin is installed, when a Bedrock Player tries to join the server, the online Admins will recieve a message saying Player X tried to join, giving them an option to allow it.     
This whitelisting works via the XBox-live XUID or XBox-User-ID much like Mojangs own UUID system.

Permissions:
- protoflow.admin : Get displayed the Player X tried to join messages
- protoflow.command.whitelistAdd : /pf whitelistadd \<Playername> \<XUID>
- protoflow.command.whitelistRemove : /pf whitelistRemove \<Playername>
- protoflow.command.whitelistRemoveXUID : /pf whitelistRemoveXUID \<XUID>
- protoflow.command.whitelistallowlastnew : /pf whitelistAllowLastNew : Allow last attempted connecting 'Bedrock-Player' to join
- protoflow.command.playerinfo : /pf playerinfo <Playername> : Displays Player connection info (Java/Bedrock and Version) 
  
### Gotchas and related:
  
  1: Currently this disables all other features of ProtocolSupport, this will be addressed shortly with a config option.   
  2: Has no whitelist list feature, will be addressed shortly.    
  3: Needs a config option for overlapping usernames, currently those work together but will inevitably cause issues with plugins such as Towny
