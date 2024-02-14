package dev.potato.tntchallenge.utilities;

import dev.potato.tntchallenge.TNTChallenge;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class TiktokGameUtility {
    private boolean isPlaying;
    private boolean isPaused;
    private Player player;

    public TiktokGameUtility(boolean isPlaying, boolean isPaused, Player player) {
        this.isPlaying = isPlaying;
        this.isPaused = isPaused;
        this.player = player;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void startGame() {
        // Initialization
        TNTChallenge plugin = TNTChallenge.getPlugin();
        player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Game has started!", ChatColor.WHITE + "" + ChatColor.BOLD + "Good luck!", 2, 60, 2);
        plugin.getTiktokLiveUtility().setPlayer(player);

        // Scoreboard
        plugin.getScoreboardUtility(player).givePlayerScoreboard(true);

        // Game Utilities
        TiktokGameUtility tiktokGameUtility = plugin.getTiktokGameUtility(player);
        tiktokGameUtility.setPlaying(true);
        tiktokGameUtility.setPaused(false);

        // Player Inventory
        player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(Material.IRON_BLOCK, 64),
                new ItemStack(Material.GOLD_BLOCK, 64),
                new ItemStack(Material.EMERALD_BLOCK, 64),
                new ItemStack(Material.DIAMOND_BLOCK, 64));
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
        player.getInventory().addItem(pickaxe);

        // Game Mode
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);

        // Place Area
        BoundingBox placeAreaBoundingBox = plugin.getRegions().getPlaceArea();
        List<Block> placeArea = plugin.getRegions().boundingBoxToList(placeAreaBoundingBox, player.getWorld());
        for (Block block : placeArea) {
            block.setType(Material.AIR);
        }
    }

    public void endGame() {
        // De-Initialization
        TNTChallenge plugin = TNTChallenge.getPlugin();
        player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Game has stopped!", ChatColor.WHITE + "" + ChatColor.BOLD + "Thanks for playing!", 2, 60, 2);

        // Scoreboard
        plugin.getScoreboardUtility(player).removePlayerScoreboard();

        // Game Utilities
        TiktokGameUtility tiktokGameUtility = plugin.getTiktokGameUtility(player);
        tiktokGameUtility.setPlaying(false);
        tiktokGameUtility.setPaused(false);

        // Player Inventory
        player.getInventory().clear();

        // Game Mode
        player.setGameMode(GameMode.CREATIVE);

        // Place Area
        BoundingBox placeAreaBoundingBox = plugin.getRegions().getPlaceArea();
        List<Block> placeArea = plugin.getRegions().boundingBoxToList(placeAreaBoundingBox, player.getWorld());
        for (Block block : placeArea) {
            block.setType(Material.AIR);
        }
    }
}