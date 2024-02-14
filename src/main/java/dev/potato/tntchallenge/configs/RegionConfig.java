package dev.potato.tntchallenge.configs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class RegionConfig {
    private static File file;
    private static FileConfiguration customFile;

    public static FileConfiguration get() {
        return customFile;
    }

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("TNTChallenge").getDataFolder(), "regions.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] Could not create regions.yml!");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] Could not save regions.yml!");
        }
    }
}
