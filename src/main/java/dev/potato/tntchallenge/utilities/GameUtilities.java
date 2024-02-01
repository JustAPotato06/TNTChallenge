package dev.potato.tntchallenge.utilities;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.tasks.TNTSpawnTask;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class GameUtilities {
    private boolean isPlaying;
    private boolean isPaused;
    private Player player;
    private BukkitTask tntSpawning;

    public GameUtilities(boolean isPlaying, boolean isPaused, Player player) {
        this.isPlaying = isPlaying;
        this.isPaused = isPaused;
        this.player = player;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public Player getPlayer() {
        return player;
    }

    public BukkitTask getTntSpawning() {
        return tntSpawning;
    }

    public void setTntSpawning(BukkitTask tntSpawning) {
        this.tntSpawning = tntSpawning;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public static void startGame(Player p) {
        // Initialization
        TNTChallenge plugin = TNTChallenge.getPlugin();
        p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Game has started!", ChatColor.WHITE + "" + ChatColor.BOLD + "Good luck!", 2, 60, 2);

        // Scoreboard
        plugin.getScoreboardUtilities().remove(p);
        plugin.getScoreboardUtilities().put(p, new PlayerScoreboardUtilities());
        PlayerScoreboardUtilities psu = plugin.getScoreboardUtilities().get(p);
        psu.givePlayerScoreboard(p, false);

        // Game Utilities
        plugin.setGameUtilities(new GameUtilities(true, false, p));

        // TNT Spawning
        plugin.getGameUtilities().setTntSpawning(new TNTSpawnTask(p).runTaskTimer(plugin, 70, 20));

        // Player Inventory
        p.getInventory().clear();
        p.getInventory().addItem(new ItemStack(Material.IRON_BLOCK, 64), new ItemStack(Material.GOLD_BLOCK, 64), new ItemStack(Material.EMERALD_BLOCK, 64), new ItemStack(Material.DIAMOND_BLOCK, 64));
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "GOD PICKAXE");
        pickaxeMeta.setUnbreakable(true);
        List<String> pickaxeLore = new ArrayList<>();
        pickaxeLore.add(ChatColor.GOLD + "This pickaxe has");
        pickaxeLore.add(ChatColor.GOLD + "unlimited durability and");
        pickaxeLore.add(ChatColor.GOLD + "VERY high efficiency!");
        pickaxeMeta.setLore(pickaxeLore);
        pickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 999, true);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxe.setItemMeta(pickaxeMeta);
        p.getInventory().addItem(pickaxe);

        // Game Mode
        p.setGameMode(GameMode.SURVIVAL);

        // Place Area
        List<Block> placeArea = RegionUtilities.boundingBoxToList(plugin.getRegions().getPlaceArea(), plugin.getGameUtilities().getPlayer().getWorld());
        for (Block block : placeArea) {
            block.setType(Material.AIR);
        }
    }

    public static void endGame(Player p) {
        // De-Initialization
        TNTChallenge plugin = TNTChallenge.getPlugin();
        p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Game has ended!", ChatColor.WHITE + "" + ChatColor.BOLD + "Thanks for playing!", 2, 60, 2);

        // Scoreboard
        plugin.getScoreboardUtilities().get(p).removePlayerScoreboard(p);
        p.getPersistentDataContainer().set(new NamespacedKey(plugin, "update-scoreboard"), PersistentDataType.BOOLEAN, false);
        plugin.getScoreboardUtilities().remove(p);

        // TNT Spawning
        plugin.getGameUtilities().getTntSpawning().cancel();

        // Game Utilities
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.setGameUtilities(new GameUtilities(false, false, null));
            }
        }.runTaskLater(plugin, 20);

        // Player Inventory
        p.getInventory().clear();

        // Game Mode
        p.setGameMode(GameMode.CREATIVE);

        // Place Area
        List<Block> placeArea = RegionUtilities.boundingBoxToList(plugin.getRegions().getPlaceArea(), plugin.getGameUtilities().getPlayer().getWorld());
        for (Block block : placeArea) {
            block.setType(Material.AIR);
        }
    }
}
