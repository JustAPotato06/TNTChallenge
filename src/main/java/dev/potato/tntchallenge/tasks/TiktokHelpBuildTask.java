package dev.potato.tntchallenge.tasks;

import dev.potato.tntchallenge.TNTChallenge;
import io.github.jwdeveloper.tiktok.data.events.gift.TikTokGiftEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TiktokHelpBuildTask extends BukkitRunnable {
    private Player p;
    private TikTokGiftEvent tikTokGiftEvent;
    private TNTChallenge plugin = TNTChallenge.getPlugin();

    public TiktokHelpBuildTask(Player p, TikTokGiftEvent tikTokGiftEvent) {
        this.p = p;
        this.tikTokGiftEvent = tikTokGiftEvent;
    }

    @Override
    public void run() {
        // Teleport the player out of the way
        p.teleport(p.getLocation().add(0, 30, 0));

        // Fill the place area
        List<Block> placeArea = plugin.getRegions().boundingBoxToList(plugin.getRegions().getPlaceArea(), p.getWorld());
        for (Block block : placeArea) {
            block.setType(Material.DIAMOND_BLOCK);
        }

        // Fill the win area
        List<Block> winArea = plugin.getRegions().boundingBoxToList(plugin.getRegions().getWinArea(), p.getWorld());
        for (Block block : winArea) {
            block.setType(Material.DIAMOND_BLOCK);
        }

        // Notify the player that they've been helped
        p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + tikTokGiftEvent.getUser().getName(), ChatColor.GOLD + "" + ChatColor.BOLD + "has sent a BUILD HELPER!", 2, 60, 2);
        p.spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 1000);

        // Start manual countdown
        new WinCountdownTask(p, winArea).runTaskTimer(plugin, 50, 30);
    }
}