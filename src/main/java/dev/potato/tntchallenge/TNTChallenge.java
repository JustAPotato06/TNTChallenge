package dev.potato.tntchallenge;

import dev.potato.tntchallenge.commands.TNTChallengeCommand;
import dev.potato.tntchallenge.configs.RegionConfig;
import dev.potato.tntchallenge.listeners.GameListeners;
import dev.potato.tntchallenge.listeners.ScoreboardListeners;
import dev.potato.tntchallenge.listeners.SetupListeners;
import dev.potato.tntchallenge.utilities.GameUtilities;
import dev.potato.tntchallenge.utilities.PlayerScoreboardUtilities;
import dev.potato.tntchallenge.utilities.PlayerSetupRegionUtilities;
import dev.potato.tntchallenge.utilities.RegionUtilities;
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

/*
    THINGS TO DO:
    - Add win logic
*/

public final class TNTChallenge extends JavaPlugin {
    private static TNTChallenge plugin;
    private RegionUtilities regions;
    private HashMap<Player, PlayerSetupRegionUtilities> setupUtilities = new HashMap<>();
    private HashMap<Player, PlayerScoreboardUtilities> scoreboardUtilities = new HashMap<>();
    private GameUtilities gameUtilities = new GameUtilities(false, false, null);

    public static TNTChallenge getPlugin() {
        return plugin;
    }

    public RegionUtilities getRegions() {
        return regions;
    }

    public void setRegions(RegionUtilities regions) {
        this.regions = regions;
    }

    public HashMap<Player, PlayerSetupRegionUtilities> getSetupUtilities() {
        return setupUtilities;
    }

    public HashMap<Player, PlayerScoreboardUtilities> getScoreboardUtilities() {
        return scoreboardUtilities;
    }

    public GameUtilities getGameUtilities() {
        return gameUtilities;
    }

    public void setGameUtilities(GameUtilities gameUtilities) {
        this.gameUtilities = gameUtilities;
    }

    @Override
    public void onEnable() {
        // Config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        RegionConfig.setup();
        RegionConfig.get().options().copyDefaults(true);
        RegionConfig.save();

        // Initialization
        plugin = this;
        loadRegionData();

        // Commands
        getCommand("tntchallenge").setExecutor(new TNTChallengeCommand());

        // Listeners
        getServer().getPluginManager().registerEvents(new SetupListeners(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardListeners(), this);
        getServer().getPluginManager().registerEvents(new GameListeners(), this);
    }

    @Override
    public void onDisable() {
        // Shutdown
        saveRegionData();
    }

    private void loadRegionData() {
        if (!RegionConfig.get().isSet("regions-id")) {
            setRegions(new RegionUtilities());
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
                RegionUtilities deserializedRegions = (RegionUtilities) bukkitIS.readObject();
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
}