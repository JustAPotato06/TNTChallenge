package dev.potato.tntchallenge.listeners;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.tasks.WinCountdownTask;
import dev.potato.tntchallenge.utilities.GameUtilities;
import dev.potato.tntchallenge.utilities.RegionUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class GameListeners implements Listener {
    private TNTChallenge plugin = TNTChallenge.getPlugin();

    @EventHandler
    public void onExplode(BlockExplodeEvent e) {
        // Ensure any blocks associated with the walls or floor aren't broken on explosion
        List<Block> wall1 = RegionUtilities.boundingBoxToList(plugin.getRegions().getWall1(), plugin.getGameUtilities().getPlayer().getWorld());
        List<Block> wall2 = RegionUtilities.boundingBoxToList(plugin.getRegions().getWall2(), plugin.getGameUtilities().getPlayer().getWorld());
        List<Block> wall3 = RegionUtilities.boundingBoxToList(plugin.getRegions().getWall3(), plugin.getGameUtilities().getPlayer().getWorld());
        List<Block> wall4 = RegionUtilities.boundingBoxToList(plugin.getRegions().getWall4(), plugin.getGameUtilities().getPlayer().getWorld());
        List<Block> floor = RegionUtilities.boundingBoxToList(plugin.getRegions().getFloor(), plugin.getGameUtilities().getPlayer().getWorld());
        e.blockList().removeAll(wall1);
        e.blockList().removeAll(wall2);
        e.blockList().removeAll(wall3);
        e.blockList().removeAll(wall4);
        e.blockList().removeAll(floor);
        // Ensure no blocks that are broken are dropped as items
        e.setYield(0);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        // Check if the game is currently active
        if (plugin.getGameUtilities().isPlaying()) {
            // Cancel any player damage
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        // Check if the game is currently active
        if (plugin.getGameUtilities().isPlaying()) {
            List<Block> placeArea = RegionUtilities.boundingBoxToList(plugin.getRegions().getPlaceArea(), plugin.getGameUtilities().getPlayer().getWorld());
            // Check if the block is not within the place area
            if (!placeArea.contains(e.getBlock())) {
                // Cancel the break
                e.setCancelled(true);
            } else {
                // Make sure the broken block doesn't drop as an item
                e.setDropItems(false);
            }
        }
    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent e) {
        // Check if the game is currently active
        if (plugin.getGameUtilities().isPlaying()) {
            // Cancel any attempt at dropping items
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        // Check if the game is currently active
        if (plugin.getGameUtilities().isPlaying()) {
            // Make sure any block that isn't in the place area isn't placed
            List<Block> placeArea = RegionUtilities.boundingBoxToList(plugin.getRegions().getPlaceArea(), plugin.getGameUtilities().getPlayer().getWorld());
            if (!placeArea.contains(e.getBlock())) {
                e.setCancelled(true);
            }
            // Make sure the player doesn't lose blocks over time
            e.getItemInHand().setAmount(64);
            // Check if the block was placed in the win area
            List<Block> winArea = RegionUtilities.boundingBoxToList(plugin.getRegions().getWinArea(), plugin.getGameUtilities().getPlayer().getWorld());
            if (!winArea.contains(e.getBlock())) return;
            // Calculate if the win area is filled
            boolean didWin = true;
            for (Block block : winArea) {
                if (block.getType() == Material.AIR) didWin = false;
            }
            // Check if the game is paused
            if (!plugin.getGameUtilities().isPaused()) {
                // Check for win
                if (didWin) {
                    // Execute win logic
                    Player p = plugin.getGameUtilities().getPlayer();
                    p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "STARTING COUNTDOWN!", "", 2, 40, 2);
                    new WinCountdownTask(p, winArea).runTaskTimer(plugin, 50, 30);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        // Check if the game is currently active
        if (plugin.getGameUtilities().isPlaying()) {
            // Check if the player that left is the game player
            Player p = e.getPlayer();
            if (plugin.getGameUtilities().getPlayer().equals(p)) {
                // End the game
                GameUtilities.endGame(p);
            }
        }
    }
}
