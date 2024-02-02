package dev.potato.tntchallenge.tasks;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerScoreboardUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.List;

public class WinCountdownTask extends BukkitRunnable {
    private Player p;
    private List<Block> winArea;
    private TNTChallenge plugin = TNTChallenge.getPlugin();
    private int countdown = plugin.getConfig().getInt("countdown-time");

    public WinCountdownTask(Player p, List<Block> winArea) {
        this.p = p;
        this.winArea = winArea;
    }

    @Override
    public void run() {
        // Check if the player is still online
        if (!p.isOnline()) this.cancel();
        // Check if the win area is still filled
        boolean shouldContinue = true;
        for (Block block : winArea) {
            if (block.getType() == Material.AIR) shouldContinue = false;
        }
        if (shouldContinue) {
            // Check if the countdown is at 0
            if (countdown == 0) {
                // Give the player a win
                p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "VICTORY!", ChatColor.WHITE + "" + ChatColor.BOLD + "Congratulations!", 4, 40, 4);
                p.spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 1000);
                BoundingBox placeAreaBoundingBox = plugin.getRegions().getPlaceArea();
                List<Block> placeArea = plugin.getRegions().boundingBoxToList(placeAreaBoundingBox, p.getWorld());
                for (Block block : placeArea) {
                    block.setType(Material.AIR);
                }
                PlayerScoreboardUtility psu = plugin.getScoreboardUtility(p);
                psu.setWins(psu.getWins() + 1);
                psu.givePlayerScoreboard(false);
                this.cancel();
            } else {
                // Decrement the countdown
                p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + countdown, ChatColor.WHITE + "" + ChatColor.BOLD + "SECONDS LEFT!", 4, 20, 4);
                countdown--;
            }
        } else {
            // Cancel the countdown
            p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "COUNTDOWN CANCELLED!", ChatColor.WHITE + "" + ChatColor.BOLD + "Better luck next time!", 4, 40, 4);
            PlayerScoreboardUtility psu = plugin.getScoreboardUtility(p);
            psu.setFailedAttempts(psu.getFailedAttempts() + 1);
            psu.givePlayerScoreboard(false);
            this.cancel();
        }
    }
}
