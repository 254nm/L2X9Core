package org.l2x9.l2x9core;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.l2x9.l2x9core.antiillegal.*;
import org.l2x9.l2x9core.antilag.*;
import org.l2x9.l2x9core.commands.*;
import org.l2x9.l2x9core.events.BlockPlace;
import org.l2x9.l2x9core.events.*;
import org.l2x9.l2x9core.patches.*;
import org.l2x9.l2x9core.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main extends JavaPlugin {

    public static long startTime;

    public static Main getPlugin() {
        return getPlugin(Main.class);
    }

    public void onEnable() {
        saveDefaultConfig();
        startTime = System.currentTimeMillis();
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&3[L2X9Core]&r&a enabled"));
        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new Offhand(), this);
        getServer().getPluginManager().registerEvents(new GateWay(), this);
        getServer().getPluginManager().registerEvents(new BookBan(), this);
        getServer().getPluginManager().registerEvents(new ChinkBan(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new CommandEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new Elytra(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new BlockRedstone(), this);
        getServer().getPluginManager().registerEvents(new WitherSpawn(), this);
        getServer().getPluginManager().registerEvents(new BlockPhysics(), this);
        getServer().getPluginManager().registerEvents(new BucketEvent(), this);
        getServer().getPluginManager().registerEvents(new MinecartLag(), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new EntityPerChunk(), this);
        // AntiIllegal events
        getServer().getPluginManager().registerEvents(new org.l2x9.l2x9core.antiillegal.BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new HopperTansfer(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        getServer().getPluginManager().registerEvents(new InventoryOpen(), this);
        getServer().getPluginManager().registerEvents(new ItemPickup(), this);
        getServer().getPluginManager().registerEvents(new PlayerScroll(), this);
        if (getConfig().getBoolean("Antiillegal.ChunkLoad-Enabled")) {
            getServer().getPluginManager().registerEvents(new ChunkLoad(), this);
        }
        // other stuff
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Utils.antiSkid();
        getCommand("say").setExecutor(new SayCommand());
        getCommand("crash").setExecutor(new CrashCommand());
        getCommand("open").setExecutor(new OpenInv());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("uuid").setExecutor(new UUidCommand());
        getCommand("uptime").setExecutor(new UptimeCommand());
        getCommand("aef").setExecutor(new BaseCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("world").setExecutor(new WorldSwitcher());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("github").setExecutor(new GithubStatsCommand());
        //Server specific events
        if (getServer().getPluginManager().getPlugin("SalC1Dupe") != null) {
            if (getSalDupeVersion().equals("1.0-SNAPSHOT")) {
                getServer().getPluginManager().registerEvents(new DupeEvt(), this);
            } else {
                Utils.println(Utils.getPrefix() + "&cThis version of SalC1Dupe is outdated Current version of SalC1Dupe on the server " + getSalDupeVersion() + " Most recent version 1.0-SNAPSHOT");
            }
        } else {
            Utils.println(Utils.getPrefix() + "&eCould not find SalC1Dupe installed on the server");
        }
    }

    public void onDisable() {
        getLogger().info("AnarchyExploitFixer disabled");
        Utils.deleteFortressDat("world");
    }

    private String getSalDupeVersion() {
        InputStream inputStream = getServer().getPluginManager().getPlugin("SalC1Dupe").getResource("plugin.yml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        FileConfiguration pluginYml = new YamlConfiguration();
        try {
            pluginYml.load(reader);
            reader.close();
        } catch (IOException | InvalidConfigurationException ignored) {
        }
        return pluginYml.getString("version");
    }
}
