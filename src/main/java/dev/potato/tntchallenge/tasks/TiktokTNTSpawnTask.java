package dev.potato.tntchallenge.tasks;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerScoreboardUtility;
import io.github.jwdeveloper.tiktok.data.events.gift.TikTokGiftEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;

public class TiktokTNTSpawnTask extends BukkitRunnable {
    private int amountOfTNT;
    private int amountOfTNTInit;
    private int powerLevel;
    private int winsToBeAdded;
    private String giftSent;
    private Player p;
    private TikTokGiftEvent tikTokGiftEvent;
    private int amountAddedToY;
    private int fuseTicks;
    private TNTChallenge plugin = TNTChallenge.getPlugin();

    public TiktokTNTSpawnTask(int amountOfTNT, int powerLevel, int winsToBeAdded, String giftSent, Player p, TikTokGiftEvent tikTokGiftEvent, int amountAddedToY, int fuseTicks) {
        this.amountOfTNT = amountOfTNT;
        this.amountOfTNTInit = amountOfTNT;
        this.powerLevel = powerLevel;
        this.winsToBeAdded = winsToBeAdded;
        this.giftSent = giftSent;
        this.p = p;
        this.tikTokGiftEvent = tikTokGiftEvent;
        this.amountAddedToY = amountAddedToY;
        this.fuseTicks = fuseTicks;
    }

    @Override
    public void run() {
        // Check if all TNT has been spawned
        if (amountOfTNT == 0) {
            this.cancel();
            return;
        }

        // Spawn TNT entity
        TNTPrimed tnt = p.getWorld().spawn(p.getLocation().add(0, amountAddedToY, 0), TNTPrimed.class);
        tnt.setFuseTicks(fuseTicks);

        // Start TNT handle explosion task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Create an artificial explosion using the defined power and remove the TNT
                p.getWorld().createExplosion(tnt.getLocation(), powerLevel, false, true);
                tnt.remove();
            }
        }.runTaskLater(plugin, fuseTicks - 10);

        if (amountOfTNT == amountOfTNTInit) {
            // Notify the player that TNT has spawned
            p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + tikTokGiftEvent.getUser().getName(), ChatColor.GOLD + "" + ChatColor.BOLD + "has sent " + giftSent, 2, 60, 2);
        }

        // Update TNT spawned and wins on scoreboard
        PlayerScoreboardUtility playerScoreboardUtility = plugin.getScoreboardUtility(p);
        playerScoreboardUtility.setTntSpawned(playerScoreboardUtility.getTntSpawned() + 1);
        if (amountOfTNT == amountOfTNTInit) {
            playerScoreboardUtility.setWins(playerScoreboardUtility.getWins() + winsToBeAdded);
        }
        playerScoreboardUtility.givePlayerScoreboard(false);

        // Decrement the counter
        amountOfTNT--;
    }
}