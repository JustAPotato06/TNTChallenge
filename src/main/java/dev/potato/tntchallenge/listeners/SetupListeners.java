package dev.potato.tntchallenge.listeners;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerSetupRegionUtility;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SetupListeners implements Listener {
    private TNTChallenge plugin = TNTChallenge.getPlugin();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        // Make sure this event only fires once for the main hand
        if (e.getHand() != EquipmentSlot.HAND) return;

        // Initialization
        Player p = e.getPlayer();
        PersistentDataContainer playerData = p.getPersistentDataContainer();

        if (p.getInventory().getItemInMainHand().getItemMeta() == null) return;

        // Check for if the player is in setup mode
        if (playerData.has(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING)) {
            // Grab what they are currently setting
            String currentSet = playerData.get(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING);
            // Make sure the action is a right click block and that they have the setup stick in hand
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.BOLD + "SETUP STICK")) {
                // Set the corner according to what the player is currently setting
                if (currentSet.equalsIgnoreCase("wall1Corner1"))
                    setCorner(p, playerData, e, "WALL 1 CORNER 1", "WALL 1 CORNER 2", "wall1Corner2");
                if (currentSet.equalsIgnoreCase("wall1Corner2"))
                    setCorner(p, playerData, e, "WALL 1 CORNER 2", "WALL 2 CORNER 1", "wall2Corner1");
                if (currentSet.equalsIgnoreCase("wall2Corner1"))
                    setCorner(p, playerData, e, "WALL 2 CORNER 1", "WALL 2 CORNER 2", "wall2Corner2");
                if (currentSet.equalsIgnoreCase("wall2Corner2"))
                    setCorner(p, playerData, e, "WALL 2 CORNER 2", "WALL 3 CORNER 1", "wall3Corner1");
                if (currentSet.equalsIgnoreCase("wall3Corner1"))
                    setCorner(p, playerData, e, "WALL 3 CORNER 1", "WALL 3 CORNER 2", "wall3Corner2");
                if (currentSet.equalsIgnoreCase("wall3Corner2"))
                    setCorner(p, playerData, e, "WALL 3 CORNER 2", "WALL 4 CORNER 1", "wall4Corner1");
                if (currentSet.equalsIgnoreCase("wall4Corner1"))
                    setCorner(p, playerData, e, "WALL 4 CORNER 1", "WALL 4 CORNER 2", "wall4Corner2");
                if (currentSet.equalsIgnoreCase("wall4Corner2"))
                    setCorner(p, playerData, e, "WALL 4 CORNER 2", "FLOOR CORNER 1", "floorCorner1");
                if (currentSet.equalsIgnoreCase("floorCorner1"))
                    setCorner(p, playerData, e, "FLOOR CORNER 1", "FLOOR CORNER 2", "floorCorner2");
                if (currentSet.equalsIgnoreCase("floorCorner2"))
                    setCorner(p, playerData, e, "FLOOR CORNER 2", "PLACE AREA CORNER 1", "placeAreaCorner1");
                if (currentSet.equalsIgnoreCase("placeAreaCorner1"))
                    setCorner(p, playerData, e, "PLACE AREA CORNER 1", "PLACE AREA CORNER 2", "placeAreaCorner2");
                if (currentSet.equalsIgnoreCase("placeAreaCorner2"))
                    setCorner(p, playerData, e, "PLACE AREA CORNER 2", "WIN AREA CORNER 1", "winAreaCorner1");
                if (currentSet.equalsIgnoreCase("winAreaCorner1"))
                    setCorner(p, playerData, e, "WIN AREA CORNER 1", "WIN AREA CORNER 2", "winAreaCorner2");
                if (currentSet.equalsIgnoreCase("winAreaCorner2"))
                    setCorner(p, playerData, e, "WIN AREA CORNER 2", "", "");
            }
        }
    }

    private void setCorner(Player p, PersistentDataContainer playerData, PlayerInteractEvent e, String beingSet, String toSet, String toSetCamelCase) {
        PlayerSetupRegionUtility setupUtility = plugin.getSetupUtility(p);
        Block blockInteracted = e.getClickedBlock();

        // Set the corner
        if (beingSet.equalsIgnoreCase("WALL 1 CORNER 1")) setupUtility.setWall1Corner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 1 CORNER 2")) setupUtility.setWall1Corner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 2 CORNER 1")) setupUtility.setWall2Corner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 2 CORNER 2")) setupUtility.setWall2Corner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 3 CORNER 1")) setupUtility.setWall3Corner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 3 CORNER 2")) setupUtility.setWall3Corner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 4 CORNER 1")) setupUtility.setWall4Corner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 4 CORNER 2")) setupUtility.setWall4Corner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("FLOOR CORNER 1")) setupUtility.setFloorCorner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("FLOOR CORNER 2")) setupUtility.setFloorCorner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("PLACE AREA CORNER 1")) setupUtility.setPlaceAreaCorner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("PLACE AREA CORNER 2")) setupUtility.setPlaceAreaCorner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("WIN AREA CORNER 1")) setupUtility.setWinAreaCorner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WIN AREA CORNER 2")) {
            setupUtility.setWinAreaCorner2(blockInteracted);

            // Take the player out of setup mode
            playerData.set(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING, toSetCamelCase);
            playerData.set(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN, false);

            // Finalize the setup
            setupUtility.finalizeSetup();

            // Notify the player
            p.sendMessage(ChatColor.GREEN + "[TNT Challenge] Setup has been completed! You can now use " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge start" + ChatColor.GREEN + " to start the game.");
            return;
        }

        // Continue setup
        playerData.set(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING, toSetCamelCase);

        // Notify the player on what they need to set next
        p.sendMessage(ChatColor.YELLOW + "You are now setting: " + ChatColor.WHITE + ChatColor.BOLD + toSet);
    }
}