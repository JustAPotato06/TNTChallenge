package dev.potato.tntchallenge.listeners;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerRegionUtilities;
import dev.potato.tntchallenge.utilities.RegionUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!e.getHand().equals(EquipmentSlot.HAND)) return;
        Player p = e.getPlayer();
        PersistentDataContainer pData = p.getPersistentDataContainer();
        if (pData.has(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING)) {
            String currentSet = pData.get(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING);
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && p.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                if (currentSet.equalsIgnoreCase("wall1Corner1")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWall1Corner1(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "wall1Corner2");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WALL 1 CORNER 1" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 1 CORNER 2");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("wall1Corner2")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWall1Corner2(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "wall2Corner1");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WALL 1 CORNER 2" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 2 CORNER 1");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("wall2Corner1")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWall2Corner1(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "wall2Corner2");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WALL 2 CORNER 1" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 2 CORNER 2");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("wall2Corner2")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWall2Corner2(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "wall3Corner1");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WALL 2 CORNER 2" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 3 CORNER 1");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("wall3Corner1")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWall3Corner1(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "wall3Corner2");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WALL 3 CORNER 1" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 3 CORNER 2");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("wall3Corner2")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWall3Corner2(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "wall4Corner1");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WALL 3 CORNER 2" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 4 CORNER 1");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("wall4Corner1")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWall4Corner1(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "wall4Corner2");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WALL 4 CORNER 1" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 4 CORNER 2");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("wall4Corner2")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWall4Corner2(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "floorCorner1");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WALL 4 CORNER 2" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "FLOOR CORNER 1");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("floorCorner1")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setFloorCorner1(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "floorCorner2");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "FLOOR CORNER 1" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "FLOOR CORNER 2");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("floorCorner2")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setFloorCorner2(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "placeAreaCorner1");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "FLOOR CORNER 2" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "PLACE AREA CORNER 1");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("placeAreaCorner1")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setPlaceAreaCorner1(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "placeAreaCorner2");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "PLACE AREA CORNER 1" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "PLACE AREA CORNER 2");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("placeAreaCorner2")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setPlaceAreaCorner2(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "winAreaCorner1");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "PLACE AREA CORNER 2" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WIN AREA CORNER 1");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("winAreaCorner1")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWinAreaCorner1(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "winAreaCorner2");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WIN AREA CORNER 1" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WIN AREA CORNER 2");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the next corner to continue.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else if (currentSet.equalsIgnoreCase("winAreaCorner2")) {
                    PlayerRegionUtilities pru = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    Block blockInteracted = e.getClickedBlock();
                    pru.setWinAreaCorner2(blockInteracted);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Success! " + ChatColor.WHITE + ChatColor.BOLD + "WIN AREA CORNER 2" + ChatColor.YELLOW + ChatColor.BOLD + " has been set.");
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You have now completed the setup!");
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You can now use the " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge start" + ChatColor.GREEN + ChatColor.BOLD + " command to start the game.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                    setRegionUtilities(pru);
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN, false);
                    TNTChallenge.getPlugin().getSetupUtilities().remove(p);
                }
            }
        }
    }

    private void setRegionUtilities(PlayerRegionUtilities pru) {
        RegionUtilities regions = TNTChallenge.getPlugin().getRegions();
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
