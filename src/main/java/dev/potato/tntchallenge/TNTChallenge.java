package dev.potato.tntchallenge;

import dev.potato.tntchallenge.commands.TNTChallengeCommand;
import dev.potato.tntchallenge.configs.RegionConfig;
import dev.potato.tntchallenge.listeners.GameListeners;
import dev.potato.tntchallenge.listeners.ScoreboardListeners;
import dev.potato.tntchallenge.listeners.SetupListeners;
import dev.potato.tntchallenge.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public final class TNTChallenge extends JavaPlugin {
    private static TNTChallenge plugin;
    private RegionUtility regions;
    private final HashMap<Player, PlayerSetupRegionUtility> setupUtilities = new HashMap<>();
    private final HashMap<Player, PlayerScoreboardUtility> scoreboardUtilities = new HashMap<>();
    private final HashMap<Player, GameUtility> gameUtilities = new HashMap<>();
    private final HashMap<Player, TiktokGameUtility> tiktokGameUtilities = new HashMap<>();
    private final TiktokLiveUtility tiktokLiveUtility = new TiktokLiveUtility();

    public static TNTChallenge getPlugin() {
        return plugin;
    }

    public RegionUtility getRegions() {
        return regions;
    }

    public void setRegions(RegionUtility regions) {
        this.regions = regions;
    }

    public PlayerSetupRegionUtility getSetupUtility(Player p) {
        PlayerSetupRegionUtility setupUtility;
        if (setupUtilities.containsKey(p)) {
            setupUtility = setupUtilities.get(p);
        } else {
            setupUtility = new PlayerSetupRegionUtility(p);
            setupUtilities.put(p, setupUtility);
        }
        return setupUtility;
    }

    public PlayerScoreboardUtility getScoreboardUtility(Player p) {
        PlayerScoreboardUtility scoreboardUtility;
        if (scoreboardUtilities.containsKey(p)) {
            scoreboardUtility = scoreboardUtilities.get(p);
        } else {
            scoreboardUtility = new PlayerScoreboardUtility(p);
            scoreboardUtilities.put(p, scoreboardUtility);
        }
        return scoreboardUtility;
    }

    public GameUtility getGameUtility(Player p) {
        GameUtility gameUtility;
        if (gameUtilities.containsKey(p)) {
            gameUtility = gameUtilities.get(p);
        } else {
            gameUtility = new GameUtility(false, false, p);
            gameUtilities.put(p, gameUtility);
        }
        return gameUtility;
    }

    public TiktokGameUtility getTiktokGameUtility(Player p) {
        TiktokGameUtility tiktokGameUtility;
        if (tiktokGameUtilities.containsKey(p)) {
            tiktokGameUtility = tiktokGameUtilities.get(p);
        } else {
            tiktokGameUtility = new TiktokGameUtility(false, false, p);
            tiktokGameUtilities.put(p, tiktokGameUtility);
        }
        return tiktokGameUtility;
    }

    public TiktokLiveUtility getTiktokLiveUtility() {
        return tiktokLiveUtility;
    }

    @Override
    public void onEnable() {
        // Config.yml
        registerConfigurations();

        // Initialization
        plugin = this;
        loadRegionData();

        // Commands
        registerCommands();

        // Listeners
        registerListeners();

        // Tiktok Live
        registerTiktokLive();
    }

    @Override
    public void onDisable() {
        // De-Initialization
        saveRegionData();
    }

    private void loadRegionData() {
        if (!RegionConfig.get().isSet("regions-id")) {
            setRegions(new RegionUtility());
            try {
                ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
                BukkitObjectOutputStream bukkitOS = new BukkitObjectOutputStream(byteOS);
                bukkitOS.writeObject(getRegions());
                bukkitOS.flush();
                byte[] serializedRegions = byteOS.toByteArray();
                String encodedRegions = Base64.getEncoder().encodeToString(serializedRegions);
                RegionConfig.get().set("regions-id", encodedRegions);
                RegionConfig.save();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[TNT Challenge] Region data successfully created and initialized!");
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] There was an error creating and initializing region data!");
            }
        } else {
            try {
                byte[] serializedRegions = Base64.getDecoder().decode(RegionConfig.get().getString("regions-id"));
                ByteArrayInputStream byteIS = new ByteArrayInputStream(serializedRegions);
                BukkitObjectInputStream bukkitIS = new BukkitObjectInputStream(byteIS);
                RegionUtility deserializedRegions = (RegionUtility) bukkitIS.readObject();
                setRegions(deserializedRegions);
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[TNT Challenge] Region data successfully loaded!");
            } catch (IOException | ClassNotFoundException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] There was an error loading region data!");
            }
        }
    }

    private void saveRegionData() {
        try {
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitOS = new BukkitObjectOutputStream(byteOS);
            bukkitOS.writeObject(getRegions());
            bukkitOS.flush();
            byte[] serializedRegions = byteOS.toByteArray();
            String encodedRegions = Base64.getEncoder().encodeToString(serializedRegions);
            RegionConfig.get().set("regions-id", encodedRegions);
            RegionConfig.save();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[TNT Challenge] Region data successfully saved!");
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] There was an error saving region data!");
        }
    }

    private void registerConfigurations() {
        // Config.yml
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Regions.yml
        RegionConfig.setup();
        RegionConfig.get().options().copyDefaults(true);
        RegionConfig.save();
    }

    private void registerCommands() {
        getCommand("tntchallenge").setExecutor(new TNTChallengeCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new SetupListeners(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardListeners(), this);
        getServer().getPluginManager().registerEvents(new GameListeners(), this);
    }

    private void registerTiktokLive() {
        if (getConfig().isSet("tiktok-username")) {
            TiktokLiveUtility.init();
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[TNT Challenge] No Tiktok username was found in the config.yml!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[TNT Challenge] Game mode will be set to singleplayer by default.");
        }
    }
}