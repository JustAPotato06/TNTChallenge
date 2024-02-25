package dev.potato.tntchallenge.utilities;

import dev.potato.tntchallenge.TNTChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.*;

public class PlayerScoreboardUtility {
    private long blocksPlaced;
    private long failedAttempts;
    private long tntSpawned;
    private long wins;
    private Player player;

    public PlayerScoreboardUtility(Player player) {
        this.player = player;
    }

    public long getBlocksPlaced() {
        return blocksPlaced;
    }

    public void setBlocksPlaced(long blocksPlaced) {
        this.blocksPlaced = blocksPlaced;
    }

    public long getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(long failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public long getTntSpawned() {
        return tntSpawned;
    }

    public void setTntSpawned(long tntSpawned) {
        this.tntSpawned = tntSpawned;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public void givePlayerScoreboard(boolean shouldInitialize) {
        TNTChallenge plugin = TNTChallenge.getPlugin();
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(new NamespacedKey(plugin, "update-scoreboard"), PersistentDataType.BOOLEAN, true);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Game Stats", Criteria.DUMMY, ChatColor.GOLD + "" + ChatColor.BOLD + "Game Stats");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        if (shouldInitialize) {
            setBlocksPlaced(0);
            setWins(0);
            setFailedAttempts(0);
            setTntSpawned(0);
        }
        Score score1 = objective.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "Blocks Placed: " + ChatColor.GOLD + ChatColor.BOLD + getBlocksPlaced());
        Score score2 = objective.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "Wins: " + ChatColor.GOLD + ChatColor.BOLD + getWins());
        Score score3 = objective.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "Failed Attempts: " + ChatColor.GOLD + ChatColor.BOLD + getFailedAttempts());
        Score score4 = objective.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "TNT Spawned: " + ChatColor.GOLD + ChatColor.BOLD + getTntSpawned());
        score4.setScore(1);
        score3.setScore(2);
        score2.setScore(3);
        score1.setScore(4);
        player.setScoreboard(scoreboard);
    }

    public void removePlayerScoreboard() {
        TNTChallenge plugin = TNTChallenge.getPlugin();
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(new NamespacedKey(plugin, "update-scoreboard"), PersistentDataType.BOOLEAN, false);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        player.setScoreboard(scoreboard);
    }
}