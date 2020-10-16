package org.l2x9.l2x9core;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.l2x9.l2x9core.alerts.GamemodeChange;
import org.l2x9.l2x9core.antiillegal.*;
import org.l2x9.l2x9core.antilag.*;
import org.l2x9.l2x9core.commands.*;
import org.l2x9.l2x9core.events.BlockPlace;
import org.l2x9.l2x9core.events.*;
import org.l2x9.l2x9core.patches.*;
import org.l2x9.l2x9core.util.DiscordWebhook;
import org.l2x9.l2x9core.util.SecondPassEvent;
import org.l2x9.l2x9core.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class L2X9Core extends JavaPlugin {
    public static long startTime;
    private final PluginManager pluginManager = getServer().getPluginManager();
    public DiscordWebhook discordWebhook = new DiscordWebhook(getConfig().getString("AlertSystem.WebhookURL"), this);
    SecondPassEvent secondPassEvent = new SecondPassEvent(getLogger(), this);
    ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

    public void onEnable() {
        new Metrics(this, 9128);
        saveDefaultConfig();
        startTime = System.currentTimeMillis();

        getLogger().info("by 254n_m enabled");
        pluginManager.registerEvents(new BlockPlace(this), this);
        pluginManager.registerEvents(new Offhand(this), this);
        pluginManager.registerEvents(new GateWay(), this);
        pluginManager.registerEvents(new BookBan(), this);
        pluginManager.registerEvents(new ChinkBan(this), this);
        pluginManager.registerEvents(new MoveEvent(this), this);
        pluginManager.registerEvents(new CommandEvent(this), this);
        pluginManager.registerEvents(new JoinEvent(this), this);
        pluginManager.registerEvents(new Elytra(this), this);
        pluginManager.registerEvents(new EntityDamageEvent(this), this);
        pluginManager.registerEvents(new BlockRedstone(this), this);
        pluginManager.registerEvents(new WitherSpawn(), this);
        pluginManager.registerEvents(new BlockPhysics(this), this);
        pluginManager.registerEvents(new BucketEvent(this), this);
        pluginManager.registerEvents(new MinecartLag(this), this);
        pluginManager.registerEvents(new PlayerChat(this), this);
        pluginManager.registerEvents(new ChestLagFix(this), this);
        //pluginManager.registerEvents(new EntityPerChunk(), this);

        // AntiIllegal events
        pluginManager.registerEvents(new org.l2x9.l2x9core.antiillegal.BlockPlace(this), this);
        pluginManager.registerEvents(new HopperTansfer(this), this);
        pluginManager.registerEvents(new InventoryClose(this), this);
        pluginManager.registerEvents(new InventoryOpen(this), this);
        pluginManager.registerEvents(new ItemPickup(this), this);
        pluginManager.registerEvents(new PlayerScroll(this), this);
        if (getConfig().getBoolean("Antiillegal.ChunkLoad-Enabled")) {
            pluginManager.registerEvents(new ChunkLoad(this), this);
        }

        // Alert system events
        if (discordWebhook.alertsEnabled()) {
            pluginManager.registerEvents(new GamemodeChange(this), this);
        }

        // other stuff
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Utils.antiSkid(this);
        getCommand("say").setExecutor(new SayCommand(this));
        getCommand("crash").setExecutor(new CrashCommand());
        getCommand("open").setExecutor(new OpenInv());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("uuid").setExecutor(new UUidCommand());
        getCommand("uptime").setExecutor(new UptimeCommand());
        getCommand("aef").setExecutor(new BaseCommand(this));
        getCommand("discord").setExecutor(new DiscordCommand(this));
        getCommand("world").setExecutor(new WorldSwitcher(this));
        getCommand("help").setExecutor(new HelpCommand(this));

        // Server specific events
        if (pluginManager.getPlugin("SalC1Dupe") != null) {
            if (getSalDupeVersion().equals("1.0-SNAPSHOT")) {
                pluginManager.registerEvents(new DupeEvt(), this);
            } else {
                Utils.println(Utils.getPrefix() + "&cThis version of SalC1Dupe is outdated Current version of SalC1Dupe on the server " + getSalDupeVersion() + " Most recent version 1.0-SNAPSHOT");
            }
        } else {
            Utils.println(Utils.getPrefix() + "&eCould not find SalC1Dupe installed on the server");
        }
        service.scheduleAtFixedRate(() -> pluginManager.callEvent(secondPassEvent), 1, 1, TimeUnit.SECONDS);
    }

    public void onDisable() {
        getLogger().info("AnarchyExploitFixer disabled");
        Utils.deleteFortressDat("world");
    }

    private String getSalDupeVersion() {
        InputStream inputStream = pluginManager.getPlugin("SalC1Dupe").getResource("plugin.yml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        FileConfiguration pluginYml = new YamlConfiguration();
        try {
            pluginYml.load(reader);
            reader.close();
        } catch (IOException | InvalidConfigurationException ignored) {
        }
        return pluginYml.getString("version");
    }

    public boolean getConfigBoolean(String path) {
        return getConfig().getBoolean(path);
    }
    public String getPingRole() {
        return getConfig().getString("AlertSystem.PingRole");
    }
}
