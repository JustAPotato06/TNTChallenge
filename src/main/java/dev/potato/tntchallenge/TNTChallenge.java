package dev.potato.tntchallenge;

import dev.potato.tntchallenge.commands.TNTChallengeCommand;
import dev.potato.tntchallenge.listeners.SetupListeners;
import dev.potato.tntchallenge.utilities.PlayerRegionUtilities;
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
COMMANDS TO ADD:
- /tntchallenge start
- /tntchallenge resetregions

THINGS TO DO:
- Clean up the setup messages to 1 line
*/

public final class TNTChallenge extends JavaPlugin {
    private static TNTChallenge plugin;
    private RegionUtilities regions;
    private HashMap<Player, PlayerRegionUtilities> setupUtilities = new HashMap<>();

    public static TNTChallenge getPlugin() {
        return plugin;
    }

    public RegionUtilities getRegions() {
        return regions;
    }

    public void setRegions(RegionUtilities regions) {
        this.regions = regions;
    }

    public HashMap<Player, PlayerRegionUtilities> getSetupUtilities() {
        return setupUtilities;
    }

    public void setSetupUtilities(HashMap<Player, PlayerRegionUtilities> setupUtilities) {
        this.setupUtilities = setupUtilities;
    }

    @Override
    public void onEnable() {
        // Config.yml
        saveDefaultConfig();

        // Initialization
        plugin = this;
        if (!getConfig().isSet("regions-id")) {
            setRegions(new RegionUtilities());
            try {
                ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
                BukkitObjectOutputStream bukkitOS = new BukkitObjectOutputStream(byteOS);
                bukkitOS.writeObject(getRegions());
                bukkitOS.flush();
                byte[] serializedRegions = byteOS.toByteArray();
                String encodedRegions = Base64.getEncoder().encodeToString(serializedRegions);
                getConfig().set("regions-id", encodedRegions);
                saveConfig();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[TNT Challenge] Region data successfully created and initialized!");
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] There was an error creating and initializing region data!");
            }
        } else if (getConfig().isSet("regions-id")) {
            try {
                byte[] serializedRegions = Base64.getDecoder().decode(getConfig().getString("regions-id"));
                ByteArrayInputStream byteIS = new ByteArrayInputStream(serializedRegions);
                BukkitObjectInputStream bukkitIS = new BukkitObjectInputStream(byteIS);
                RegionUtilities deserializedRegions = (RegionUtilities) bukkitIS.readObject();
                setRegions(deserializedRegions);
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[TNT Challenge] Region data successfully loaded!");
            } catch (IOException | ClassNotFoundException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] There was an error loading region data!");
            }
        }

        // Commands
        getCommand("tntchallenge").setExecutor(new TNTChallengeCommand());

        // Listeners
        getServer().getPluginManager().registerEvents(new SetupListeners(), this);
    }

    @Override
    public void onDisable() {
        // De-Initialization
        try {
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitOS = new BukkitObjectOutputStream(byteOS);
            bukkitOS.writeObject(getRegions());
            bukkitOS.flush();
            byte[] serializedRegions = byteOS.toByteArray();
            String encodedRegions = Base64.getEncoder().encodeToString(serializedRegions);
            getConfig().set("regions-id", encodedRegions);
            saveConfig();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[TNT Challenge] Region data successfully saved!");
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] There was an error saving region data!");
        }
    }
}
