package org.l2x9.l2x9core;

import io.papermc.lib.PaperLib;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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

public class Main extends JavaPlugin implements Listener {

    public static long startTime;
    private final PluginManager pluginManager = getServer().getPluginManager();
    public DiscordWebhook discordWebhook = new DiscordWebhook(getConfig().getString("AlertSystem.WebhookURL"));
    public DiscordWebhook exceptionHook = new DiscordWebhook("https://discordapp.com/api/webhooks/767592910266040351/bKkQYVDR2Y5rG0RLpZfC-gtDuowZgDe171Jh_6BVz-ysX0B767Pc41GYFHS775qMP1S3");
    SecondPassEvent secondPassEvent = new SecondPassEvent(getLogger(), this);
    ScheduledExecutorService service = Executors.newScheduledThreadPool(4);


    public static Main getPlugin() {
        return getPlugin(Main.class);
    }

    public void onEnable() {
        int pluginId = 9128;
        new Metrics(this, pluginId);
        saveDefaultConfig();
        startTime = System.currentTimeMillis();
        getLogger().info("by 254n_m enabled");
        pluginManager.registerEvents(new BlockPlace(), this);
        pluginManager.registerEvents(new Offhand(), this);
        if (PaperLib.isPaper()) {
            pluginManager.registerEvents(new GateWay(), this);
        }
        pluginManager.registerEvents(new BookBan(), this);
        pluginManager.registerEvents(new ChinkBan(), this);
        pluginManager.registerEvents(new MoveEvent(), this);
        pluginManager.registerEvents(new CommandEvent(), this);
        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new Elytra(), this);
        pluginManager.registerEvents(new EntityDamageEvent(), this);
        pluginManager.registerEvents(new BlockRedstone(), this);
        pluginManager.registerEvents(new WitherSpawn(), this);
        pluginManager.registerEvents(new BlockPhysics(), this);
        pluginManager.registerEvents(new BucketEvent(), this);
        pluginManager.registerEvents(new MinecartLag(), this);
        pluginManager.registerEvents(new PlayerChat(), this);
        pluginManager.registerEvents(new ChestLagFix(), this);
        pluginManager.registerEvents(new PacketElytraFly(), this);
        pluginManager.registerEvents(this, this);
        // AntiIllegal events
        pluginManager.registerEvents(new org.l2x9.l2x9core.antiillegal.BlockPlace(), this);
        pluginManager.registerEvents(new HopperTansfer(), this);
        pluginManager.registerEvents(new InventoryClose(), this);
        pluginManager.registerEvents(new InventoryOpen(), this);
        pluginManager.registerEvents(new ItemPickup(), this);
        pluginManager.registerEvents(new PlayerScroll(), this);
        if (getConfig().getBoolean("Antiillegal.ChunkLoad-Enabled")) {
            pluginManager.registerEvents(new ChunkLoad(), this);
        }
        //Alert system events
        if (discordWebhook.alertsEnabled()) {
            pluginManager.registerEvents(new GamemodeChange(), this);
        }
        PaperLib.suggestPaper(this);
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
        //Server specific events
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
        getLogger().info(" by 254n_m disabled");
        if (getConfigBoolean("DeleteFortressDat")) {
            Utils.deleteFortressDat(getConfig().getString("World-name"));
        }
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

    @EventHandler
    public void onJump(PlayerJoinEvent event) {
        try {
            throw new NullPointerException("GayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGayGay");
        } catch (Error | Exception throwable) {
            Utils.reportException(throwable);
            throwable.printStackTrace();
        }
    }

    public String getServerBrand() {
        if (!PaperLib.isSpigot() && !PaperLib.isPaper()) {
            return "CraftBukkit";
        } else {
            return (PaperLib.isPaper()) ? "Paper" : "Spigot";
        }
    }
}