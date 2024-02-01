package dev.potato.tntchallenge.listeners;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerScoreboardUtilities;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ScoreboardListeners implements Listener {
    private TNTChallenge plugin = TNTChallenge.getPlugin();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        // Initialization
        Player p = e.getPlayer();
        PersistentDataContainer pData = p.getPersistentDataContainer();
        // Check if the player has a scoreboard that should be updated
        if (pData.has(new NamespacedKey(plugin, "update-scoreboard"), PersistentDataType.BOOLEAN)) {
            if (pData.get(new NamespacedKey(plugin, "update-scoreboard"), PersistentDataType.BOOLEAN)) {
                // Update the blocks placed scoreboard value
                PlayerScoreboardUtilities scoreboardUtility = plugin.getScoreboardUtilities().get(p);
                scoreboardUtility.setBlocksPlaced(scoreboardUtility.getBlocksPlaced() + 1);
                scoreboardUtility.givePlayerScoreboard(p, true);
            }
        }
    }
}
