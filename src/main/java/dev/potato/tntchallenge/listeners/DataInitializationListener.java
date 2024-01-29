package dev.potato.tntchallenge.listeners;

import dev.potato.tntchallenge.TNTChallenge;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DataInitializationListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PersistentDataContainer pData = p.getPersistentDataContainer();
        pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN, false);
    }
}
