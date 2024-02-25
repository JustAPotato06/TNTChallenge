package dev.potato.tntchallenge.tasks;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerScoreboardUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TNTSpawnTask extends BukkitRunnable {
    private Player p;
    private TNTChallenge plugin = TNTChallenge.getPlugin();
    private boolean isCooldown;
    private Random r = new Random();

    public TNTSpawnTask(Player p) {
        this.p = p;
    }

    @Override
    public void run() {
        // Check for if the game is paused
        if (plugin.getSingeplayerGameUtility(p).isPaused()) return;

        // Check for cooldown
        if (!isCooldown) {
            // Generate Random Chance
            int shouldSpawn = r.nextInt(100);

            // Check for TNT spawn
            int spawnChance = plugin.getConfig().getInt("spawn-chance");
            if (shouldSpawn < spawnChance) {
                // Generate power
                int min = plugin.getConfig().getInt("min-tnt-power");
                int max = plugin.getConfig().getInt("max-tnt-power");
                int power = r.nextInt((max - min) + 1) + min;

                // Spawn TNT entity
                TNTPrimed tnt = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
                int detonateTime = plugin.getConfig().getInt("tnt-detonate-time");
                tnt.setFuseTicks(detonateTime);

                // Start TNT handle explosion task
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        // Create an artificial explosion using the defined power and remove the TNT
                        p.getWorld().createExplosion(tnt.getLocation(), power, false, true);
                        tnt.remove();
                    }
                }.runTaskLater(plugin, detonateTime - 10);

                // Notify the player that TNT has spawned
                p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "TNT HAS SPAWNED!", ChatColor.GOLD + "" + ChatColor.BOLD + "Power Level: " + ChatColor.WHITE + ChatColor.BOLD + power, 2, 60, 2);

                // Update TNT spawned on scoreboard
                PlayerScoreboardUtility playerScoreboardUtility = plugin.getScoreboardUtility(p);
                playerScoreboardUtility.setTntSpawned(playerScoreboardUtility.getTntSpawned() + 1);
                playerScoreboardUtility.givePlayerScoreboard(false);

                // Activate the TNT spawn cooldown
                isCooldown = true;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        isCooldown = false;
                    }
                }.runTaskLater(plugin, 40);
            }
        }
    }
}