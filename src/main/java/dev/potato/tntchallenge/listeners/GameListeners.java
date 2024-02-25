package dev.potato.tntchallenge.listeners;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.tasks.WinCountdownTask;
import dev.potato.tntchallenge.utilities.PlayerScoreboardUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.BoundingBox;

import java.util.List;

public class GameListeners implements Listener {
    private TNTChallenge plugin = TNTChallenge.getPlugin();
    private boolean isTikTokConnected = plugin.getConfig().isSet("tiktok-username");

    @EventHandler
    public void onExplode(BlockExplodeEvent e) {
        Block block = e.getBlock();
        World world = block.getWorld();

        // Ensure any blocks associated with the walls or floor aren't broken on explosion
        List<Block> wall1 = plugin.getRegions().boundingBoxToList(plugin.getRegions().getWall1(), world);
        List<Block> wall2 = plugin.getRegions().boundingBoxToList(plugin.getRegions().getWall2(), world);
        List<Block> wall3 = plugin.getRegions().boundingBoxToList(plugin.getRegions().getWall3(), world);
        List<Block> wall4 = plugin.getRegions().boundingBoxToList(plugin.getRegions().getWall4(), world);
        List<Block> floor = plugin.getRegions().boundingBoxToList(plugin.getRegions().getFloor(), world);
        e.blockList().removeAll(wall1);
        e.blockList().removeAll(wall2);
        e.blockList().removeAll(wall3);
        e.blockList().removeAll(wall4);
        e.blockList().removeAll(floor);

        // Ensure no blocks that are broken are dropped as items
        for (Entity entity : world.getNearbyEntities(block.getLocation(), 100, 100, 100)) {
            if (entity instanceof Player p) {
                if (isTikTokConnected) {
                    if (plugin.getTiktokGameUtility(p).isPlaying()) e.setYield(0);
                } else {
                    if (plugin.getSingeplayerGameUtility(p).isPlaying()) e.setYield(0);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            // Cancel any player damage
            if (isTikTokConnected) {
                if (plugin.getTiktokGameUtility(p).isPlaying()) e.setCancelled(true);
            } else {
                if (plugin.getSingeplayerGameUtility(p).isPlaying()) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerHungerDeplete(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player p) {
            // Cancel any player hunger
            if (isTikTokConnected) {
                if (plugin.getTiktokGameUtility(p).isPlaying()) e.setCancelled(true);
            } else {
                if (plugin.getSingeplayerGameUtility(p).isPlaying()) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        // Handle block breaking
        if (isTikTokConnected) {
            if (plugin.getTiktokGameUtility(p).isPlaying()) handleBlockBreak(e, p);
        } else {
            if (plugin.getSingeplayerGameUtility(p).isPlaying()) handleBlockBreak(e, p);
        }
    }

    private void handleBlockBreak(BlockBreakEvent e, Player p) {
        List<Block> placeArea = plugin.getRegions().boundingBoxToList(plugin.getRegions().getPlaceArea(), p.getWorld());
        // Check if the block is not within the place area
        if (!placeArea.contains(e.getBlock())) {
            // Cancel the break
            e.setCancelled(true);
        } else {
            // Make sure the broken block doesn't drop as an item
            e.setDropItems(false);
        }
    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        // Cancel any attempt at dropping items
        if (isTikTokConnected) {
            if (plugin.getTiktokGameUtility(p).isPlaying()) e.setCancelled(true);
        } else {
            if (plugin.getSingeplayerGameUtility(p).isPlaying()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        boolean shouldAutocompleteBlocks = plugin.getConfig().getBoolean("should-autocomplete-blocks");

        // Handle block placing
        if (isTikTokConnected) {
            // Check if the game is currently active
            if (!plugin.getTiktokGameUtility(p).isPlaying()) return;
            handleBlockPlace(e, p, shouldAutocompleteBlocks);

            // Check if the game is paused
            if (plugin.getTiktokGameUtility(p).isPaused()) return;

            // Handle win logic
            handleWin(e, p);
        } else {
            // Check if the game is currently active
            if (!plugin.getSingeplayerGameUtility(p).isPlaying()) return;
            handleBlockPlace(e, p, shouldAutocompleteBlocks);

            // Check if the game is paused
            if (plugin.getSingeplayerGameUtility(p).isPaused()) return;

            // Handle win logic
            handleWin(e, p);
        }
    }

    private void handleBlockPlace(BlockPlaceEvent e, Player p, boolean shouldAutocompleteBlocks) {
        PersistentDataContainer playerData = p.getPersistentDataContainer();

        // Make sure any block that isn't in the place area isn't placed
        List<Block> placeArea = plugin.getRegions().boundingBoxToList(plugin.getRegions().getPlaceArea(), p.getWorld());
        if (!placeArea.contains(e.getBlock())) e.setCancelled(true);

        // Make sure the player doesn't lose blocks over time
        e.getItemInHand().setAmount(64);

        // Auto complete blocks
        if (shouldAutocompleteBlocks) autocompleteBlock(e);

        // Check if the player has a scoreboard that should be updated
        if (playerData.has(new NamespacedKey(plugin, "update-scoreboard"), PersistentDataType.BOOLEAN)) {
            boolean shouldUpdate = playerData.get(new NamespacedKey(plugin, "update-scoreboard"), PersistentDataType.BOOLEAN);
            if (shouldUpdate) {
                // Update the blocks placed scoreboard value
                PlayerScoreboardUtility playerScoreboardUtility = plugin.getScoreboardUtility(p);
                playerScoreboardUtility.setBlocksPlaced(playerScoreboardUtility.getBlocksPlaced() + 1);
                playerScoreboardUtility.givePlayerScoreboard(false);
            }
        }
    }

    private void handleWin(BlockPlaceEvent e, Player p) {
        // Check if the block was placed in the win area
        List<Block> winArea = plugin.getRegions().boundingBoxToList(plugin.getRegions().getWinArea(), p.getWorld());
        if (!winArea.contains(e.getBlock())) return;

        // Calculate if the win area is filled
        boolean didWin = true;
        for (Block block : winArea) {
            if (block.getType() == Material.AIR) didWin = false;
        }

        // Execute win logic
        if (didWin) {
            p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "STARTING COUNTDOWN!", "", 2, 40, 2);
            new WinCountdownTask(p, winArea).runTaskTimer(plugin, 50, 30);
        }
    }

    private void autocompleteBlock(BlockPlaceEvent e) {
        BoundingBox placeArea = plugin.getRegions().getPlaceArea();
        Block block = e.getBlock();

        // Initialize variables
        int lowestY = (int) placeArea.getMinY();
        int blockY = block.getY();
        boolean isIron = lowestY <= blockY && blockY <= (lowestY + 2);
        boolean isGold = (lowestY + 2) < blockY && blockY <= (lowestY + 5);
        boolean isEmerald = (lowestY + 5) < blockY && blockY <= (lowestY + 8);
        boolean isDiamond = (lowestY + 8) < blockY && blockY <= (lowestY + 11);

        // Check for position and set the block as needed
        if (isIron) {
            block.setType(Material.IRON_BLOCK);
        } else if (isGold) {
            block.setType(Material.GOLD_BLOCK);
        } else if (isEmerald) {
            block.setType(Material.EMERALD_BLOCK);
        } else if (isDiamond) {
            block.setType(Material.DIAMOND_BLOCK);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        // Handle player leaving
        if (isTikTokConnected) {
            if (plugin.getTiktokGameUtility(p).isPlaying()) plugin.getTiktokGameUtility(p).endGame();
        } else {
            if (plugin.getSingeplayerGameUtility(p).isPlaying()) plugin.getSingeplayerGameUtility(p).endGame();
        }
    }
}