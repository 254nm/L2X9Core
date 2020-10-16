# L2X9Core

___

## Features

* Prevent lag machines 
* Prevent all crash exploits that i know of
* Prevent ChunkBan
* Elytra speed limit
* Patch BookBan without disabling shulker peek
* Prevent EndPortal greifing
* Patch ChestLag
* Patch players using the OffHand crash module in certan hacked clients to crash the server with books
* Prevent players from using illegal items with a very good anti illegal that can check shulkers in chests
* Discord alerts
* Nether roof and bottom fix
* Disable BlockPhysics at low tps to prevent server crashes
* Disable RedStone at bad tps to prevent players from crashing the server with lag machines
* Disable Elytra at low tps to prevent more server crashes
* Limit player spawnable entites per chunk to prevent players from lagging the server
* Remove illegal potion effects from players
* AntiSpam and AntiAdvertisment
* Active devlopment
* Configureable /help command

___

## Config

```yml
# L2X9Core by 254n-m

#Messages to send the player when they try to place illegal blocks
IllegalBlock-Place:
  Bedrock: '[&b&lL2X9&r&3&lCore&r] &6That block is not allowed'
  Barrier: '[&b&lL2X9&r&3&lCore&r] &6That block is not allowed'
  End_Portal_Frame: '[&b&lL2X9&r&3&lCore&r] &6That block is not allowed'
  Mob_Spawner: '[&b&lL2X9&r&3&lCore&r] &6That block is not allowed'
#ChunkBan skull limit tile entity limit and prevent message
ChunkBan:
  Prevent-Message: '[&b&lL2X9&r&3&lCore&r] &6ChunkBan has been disabled due to an exploit (^:'
  TileEntity-Max: 500
  Skull-Max: 100
#Nether top and bottom layers
Nether:
  Top-Layer: 127
  Top-message: '[&b&lL2X9&r&3&lCore&r] &6The nether top has been disabled due to lag'
  Bottom-Layer: 0
  Bottom-message: '[&b&lL2X9&r&3&lCore&r] &6The nether bottom has been disabled due to lag'
  top-bottom-do-damage: true
#Radius around spawn to disable /home in
#Set 0 to disable
Spawn:
  Raidus: 500
  Message: "&6You must be&r&c %r% &r&6 blocks from spawn to use /home "
#Message to send to the server when a player first joins
#Use {Player} as a place holder for the players name
FirstJoin:
  Message: '&c{Player}&r&7 Has joined the server for the first time'
#Elytra/redstone disable TPS values keep in mind this is a double so it can be like 13.2255
#Set 0 to disable 
Elytra:
  Disable-TPS: 12
Redstone:
  Disable-TPS: 16
  Amount-per-chunk: 16
#Anti LightLag
LightingLag-StepIn:
  TPS: 17
#Misc config shit
#Message to send when a player tries to use a 32k or strength 255
IllegalDamage:
  Message: "[&b&lL2X9&r&3&lCore&r]&6 How did you even get a 32k out to begin with"
#Player attempts to use or drink an illegal potion or use an illegal tipped arrow
IllegalPotion:
  Message: "[&b&lL2X9&r&3&lCore&r]&6 Sorry those are patched"
#Message to send the player when they use an elytra in low tps use {tps} to get the elytra disable tps
ElytraLowTPS:
  Message: "[&b&lL2X9&r&3&lCore&r] &6Elytras are disabled if the tps is below&r&c {tps}"
UnbookBan:
  Message: "[&b&lL2X9&r&3&lCore&r]&6 You have been unbookbanned"
#Message for /discord use & for colors
Discord: "&6Join us on discord at &r&c<Your discord here>"
#Ammount of vehicles allowed in a chunk
Minecart-per-chunk:
  limit: 20
#This is like discord slowmode but for the minecraft chat the cool down is in seconds
Chat:
  Cooldown: 3
  #Adding a word to this list will stop the message from being broadcasted to chat but it will be seen by the player who send the message so the player wont know that their message wasnt sent
  Blocked-words:
    - "discord"
    - "."
    - "dot"
#TP on an entity exploit prevent message
tp.prevent:
  message: "[&b&lL2X9&r&3&lCore&r] &6Patched"
#World name for the /world command and the fortress dat deleter
World-name: "world"
#AntiIllegal check toggles toggles
#Only enable ChunkLoad of you have a massive problem with illegals on your server
Antiillegal:
  Block-Place-Enabled: true
  ChunkLoad-Enabled: false
  HopperTransfer-Enabled: true
  InventoryClose-Enabled: true
  InventoryOpen-Enabled: true
  PlayerHotbarMove-Enabled: true
  ItemPickup: true
  Check-Illegal-Damage: true
  Illegal-Items-List:
    - "BEDROCK"
    - "COMMAND_REPEATING"
    - "COMMAND_MINECART"
    - "COMMAND_CHAIN"
    - "COMMAND"
    - "ENDER_PORTAL_FRAME"
    - "KNOWLEDGE_BOOK"
    - "MOB_SPAWNER"
    - "PORTAL"
    - "STRUCTURE_BLOCK"
    - "STRUCTURE_VOID"
#Help menu use & for colors
help:
  - "&6-----------------------------------------------------"
  - "&3/ignore <name> to ignore a player."
  - "&3/ignorelist to list ignored players."
  - "&3/r <message> to reply to the last person that messaged you."
  - "&3/l <message> to message the last person that you messaged."
  - "&3/w <name> <message> OR /msg <name> <message> to pm a player."
  - "&3/toggleconnectionmsgs to toggle join and leave messages."
  - "&3/togglechat to toggle the default chat."
  - "&3/toggledeathmsg to toggle all death messages."
  - "&3/kill to kill yourself."
  - "&3/stats to get world size and how many players have joined."
  - "&3/playtime to see a player's total playtime."
  - "&3/mostplaytime to show top playtimes."
  - "&3/discord to join the L2X9 discord."
  - "&6-----------------------------------------------------"
#Formatting for the say command use {message} as a place holder for the message use & for colours
#Colour codes also work in the message
SayCommandFormat: "[&b&lL2X9&r] {message}"
#water / lava flowing disable tps this is useful on new servers with lots of block physics updates that cause lag
#Set 0 to disable
BlockPhysics-disable-tps: 15
#This is to prevent people from crashing / lagging the server by spam opening chest and other containers
ChestLagFix:
  MaxOpensPerSecond: 16
  KickMessage: "[&b&lL2X9&r&3&lCore&r]&c [AntiChestLag]&r&6 You have lost connection to the server"
  RemoveUnicodeBooks: false
#What should we notify you of on discord
AlertSystem:
  Alerts-Enabled: true
  WebhookURL: ""
  #What role do we ping to get this ping the role you want but put a backslash there (i.e \@Owner) then copy paste the tag it gives you on discord and paste it here
  PingRole: "@everyone"
  #What events do we need to alert you of
  LagMachineRemoval: true
  ChunkBanAttempt: true
  OffhandServerCrash: true
  ChestLagFix: true
  IllegalItemAlert: true
  PreventEndPortalDestroy: true
  IllegalBlockPlace: true
  OppedPlayerJoin: true
```

___

## Commands

* /say ~ Configureable /say command with support for colours
* /crash ~ Crash the game of any player / bot
* /open ~ Open the Inventory / EnderChest of any player
* /speed ~ Turn your creative mode fly speed up
* /uuid ~ Gets the uuid of the spesified player
* /uptime ~ Shows the uptime of the server
* /aef ~ Base command of the plugin
* /discord ~ Show the discord of your server
* /world ~ Teleport to the spesified world
* /help ~ Shows the help menu

___

## bStats

![bStats](https://bstats.org/signatures/bukkit/L2X9Core.svg)


