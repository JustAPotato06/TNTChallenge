package dev.potato.tntchallenge.listeners;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerSetupRegionUtilities;
import dev.potato.tntchallenge.utilities.RegionUtilities;
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
import org.bukkit.util.BoundingBox;

public class SetupListeners implements Listener {
    private TNTChallenge plugin = TNTChallenge.getPlugin();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        // Make sure this event only fires once for the main hand
        if (e.getHand() != EquipmentSlot.HAND) return;
        // Initialization
        Player p = e.getPlayer();
        PersistentDataContainer pData = p.getPersistentDataContainer();
        // Check for if the player is in setup mode
        if (pData.has(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING)) {
            // Grab what they are currently setting
            String currentSet = pData.get(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING);
            // Make sure the action is a right click block and that they have the setup stick in hand
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.BOLD + "SETUP STICK")) {
                // Set the corner according to what the player is currently setting
                if (currentSet.equalsIgnoreCase("wall1Corner1"))
                    setCorner(p, pData, e, "WALL 1 CORNER 1", "WALL 1 CORNER 2", "wall1Corner2");
                if (currentSet.equalsIgnoreCase("wall1Corner2"))
                    setCorner(p, pData, e, "WALL 1 CORNER 2", "WALL 2 CORNER 1", "wall2Corner1");
                if (currentSet.equalsIgnoreCase("wall2Corner1"))
                    setCorner(p, pData, e, "WALL 2 CORNER 1", "WALL 2 CORNER 2", "wall2Corner2");
                if (currentSet.equalsIgnoreCase("wall2Corner2"))
                    setCorner(p, pData, e, "WALL 2 CORNER 2", "WALL 3 CORNER 1", "wall3Corner1");
                if (currentSet.equalsIgnoreCase("wall3Corner1"))
                    setCorner(p, pData, e, "WALL 3 CORNER 1", "WALL 3 CORNER 2", "wall3Corner2");
                if (currentSet.equalsIgnoreCase("wall3Corner2"))
                    setCorner(p, pData, e, "WALL 3 CORNER 2", "WALL 4 CORNER 1", "wall4Corner1");
                if (currentSet.equalsIgnoreCase("wall4Corner1"))
                    setCorner(p, pData, e, "WALL 4 CORNER 1", "WALL 4 CORNER 2", "wall4Corner2");
                if (currentSet.equalsIgnoreCase("wall4Corner2"))
                    setCorner(p, pData, e, "WALL 4 CORNER 2", "FLOOR CORNER 1", "floorCorner1");
                if (currentSet.equalsIgnoreCase("floorCorner1"))
                    setCorner(p, pData, e, "FLOOR CORNER 1", "FLOOR CORNER 2", "floorCorner2");
                if (currentSet.equalsIgnoreCase("floorCorner2"))
                    setCorner(p, pData, e, "FLOOR CORNER 2", "PLACE AREA CORNER 1", "placeAreaCorner1");
                if (currentSet.equalsIgnoreCase("placeAreaCorner1"))
                    setCorner(p, pData, e, "PLACE AREA CORNER 1", "PLACE AREA CORNER 2", "placeAreaCorner2");
                if (currentSet.equalsIgnoreCase("placeAreaCorner2"))
                    setCorner(p, pData, e, "PLACE AREA CORNER 2", "WIN AREA CORNER 1", "winAreaCorner1");
                if (currentSet.equalsIgnoreCase("winAreaCorner1"))
                    setCorner(p, pData, e, "WIN AREA CORNER 1", "WIN AREA CORNER 2", "winAreaCorner2");
                // Final corner logic
                if (currentSet.equalsIgnoreCase("winAreaCorner2")) {
                    // Set the corner
                    PlayerSetupRegionUtilities pru = plugin.getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWinAreaCorner2(blockInteracted);
                    pData.set(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING, "");
                    // Save all corners to the persistent region utility object
                    setRegionUtilities(pru);
                    // Take the player out of setup mode
                    pData.set(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN, false);
                    plugin.getSetupUtilities().remove(p);
                    p.sendMessage(ChatColor.GREEN + "[TNT Challenge] Setup has been completed! You can now use " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge start" + ChatColor.GREEN + " to start the game.");
                }
            }
        }
    }

    private void setCorner(Player p, PersistentDataContainer pData, PlayerInteractEvent e, String beingSet, String toSet, String toSetCamelCase) {
        PlayerSetupRegionUtilities pru = plugin.getSetupUtilities().get(p);
        Block blockInteracted = e.getClickedBlock();
        if (beingSet.equalsIgnoreCase("WALL 1 CORNER 1")) pru.setWall1Corner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 1 CORNER 2")) pru.setWall1Corner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 2 CORNER 1")) pru.setWall2Corner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 2 CORNER 2")) pru.setWall2Corner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 3 CORNER 1")) pru.setWall3Corner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 3 CORNER 2")) pru.setWall3Corner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 4 CORNER 1")) pru.setWall4Corner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WALL 4 CORNER 2")) pru.setWall4Corner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("FLOOR CORNER 1")) pru.setFloorCorner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("FLOOR CORNER 2")) pru.setFloorCorner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("PLACE AREA CORNER 1")) pru.setPlaceAreaCorner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("PLACE AREA CORNER 2")) pru.setPlaceAreaCorner2(blockInteracted);
        if (beingSet.equalsIgnoreCase("WIN AREA CORNER 1")) pru.setWinAreaCorner1(blockInteracted);
        if (beingSet.equalsIgnoreCase("WIN AREA CORNER 2")) pru.setWinAreaCorner2(blockInteracted);
        pData.set(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING, toSetCamelCase);
        p.sendMessage(ChatColor.YELLOW + "You are now setting: " + ChatColor.WHITE + ChatColor.BOLD + toSet);
    }

    private void setRegionUtilities(PlayerSetupRegionUtilities pru) {
        RegionUtilities regions = plugin.getRegions();
        Block wall1Corner1 = pru.getWall1Corner1();
        Block wall1Corner2 = pru.getWall1Corner2();
        regions.setWall1(new BoundingBox(wall1Corner1.getX(), wall1Corner1.getY(), wall1Corner1.getZ(), wall1Corner2.getX(), wall1Corner2.getY(), wall1Corner2.getZ()));
        Block wall2Corner1 = pru.getWall2Corner1();
        Block wall2Corner2 = pru.getWall2Corner2();
        regions.setWall2(new BoundingBox(wall2Corner1.getX(), wall2Corner1.getY(), wall2Corner1.getZ(), wall2Corner2.getX(), wall2Corner2.getY(), wall2Corner2.getZ()));
        Block wall3Corner1 = pru.getWall3Corner1();
        Block wall3Corner2 = pru.getWall3Corner2();
        regions.setWall3(new BoundingBox(wall3Corner1.getX(), wall3Corner1.getY(), wall3Corner1.getZ(), wall3Corner2.getX(), wall3Corner2.getY(), wall3Corner2.getZ()));
        Block wall4Corner1 = pru.getWall4Corner1();
        Block wall4Corner2 = pru.getWall4Corner2();
        regions.setWall4(new BoundingBox(wall4Corner1.getX(), wall4Corner1.getY(), wall4Corner1.getZ(), wall4Corner2.getX(), wall4Corner2.getY(), wall4Corner2.getZ()));
        Block floorCorner1 = pru.getFloorCorner1();
        Block floorCorner2 = pru.getFloorCorner2();
        regions.setFloor(new BoundingBox(floorCorner1.getX(), floorCorner1.getY(), floorCorner1.getZ(), floorCorner2.getX(), floorCorner2.getY(), floorCorner2.getZ()));
        Block placeAreaCorner1 = pru.getPlaceAreaCorner1();
        Block placeAreaCorner2 = pru.getPlaceAreaCorner2();
        regions.setPlaceArea(new BoundingBox(placeAreaCorner1.getX(), placeAreaCorner1.getY(), placeAreaCorner1.getZ(), placeAreaCorner2.getX(), placeAreaCorner2.getY(), placeAreaCorner2.getZ()));
        Block winAreaCorner1 = pru.getWinAreaCorner1();
        Block winAreaCorner2 = pru.getWinAreaCorner2();
        regions.setWinArea(new BoundingBox(winAreaCorner1.getX(), winAreaCorner1.getY(), winAreaCorner1.getZ(), winAreaCorner2.getX(), winAreaCorner2.getY(), winAreaCorner2.getZ()));
        regions.setSetup(true);
    }
}