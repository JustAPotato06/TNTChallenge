package dev.potato.tntchallenge.utilities;

import dev.potato.tntchallenge.TNTChallenge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.BoundingBox;

public class PlayerSetupRegionUtility {
    private Block wall1Corner1;
    private Block wall1Corner2;
    private Block wall2Corner1;
    private Block wall2Corner2;
    private Block wall3Corner1;
    private Block wall3Corner2;
    private Block wall4Corner1;
    private Block wall4Corner2;
    private Block floorCorner1;
    private Block floorCorner2;
    private Block placeAreaCorner1;
    private Block placeAreaCorner2;
    private Block winAreaCorner1;
    private Block winAreaCorner2;
    private Player player;

    public PlayerSetupRegionUtility(Player player) {
        this.player = player;
    }

    public Block getWall1Corner1() {
        return wall1Corner1;
    }

    public void setWall1Corner1(Block wall1Corner1) {
        this.wall1Corner1 = wall1Corner1;
    }

    public Block getWall1Corner2() {
        return wall1Corner2;
    }

    public void setWall1Corner2(Block wall1Corner2) {
        this.wall1Corner2 = wall1Corner2;
    }

    public Block getWall2Corner1() {
        return wall2Corner1;
    }

    public void setWall2Corner1(Block wall2Corner1) {
        this.wall2Corner1 = wall2Corner1;
    }

    public Block getWall2Corner2() {
        return wall2Corner2;
    }

    public void setWall2Corner2(Block wall2Corner2) {
        this.wall2Corner2 = wall2Corner2;
    }

    public Block getWall3Corner1() {
        return wall3Corner1;
    }

    public void setWall3Corner1(Block wall3Corner1) {
        this.wall3Corner1 = wall3Corner1;
    }

    public Block getWall3Corner2() {
        return wall3Corner2;
    }

    public void setWall3Corner2(Block wall3Corner2) {
        this.wall3Corner2 = wall3Corner2;
    }

    public Block getWall4Corner1() {
        return wall4Corner1;
    }

    public void setWall4Corner1(Block wall4Corner1) {
        this.wall4Corner1 = wall4Corner1;
    }

    public Block getWall4Corner2() {
        return wall4Corner2;
    }

    public void setWall4Corner2(Block wall4Corner2) {
        this.wall4Corner2 = wall4Corner2;
    }

    public Block getFloorCorner1() {
        return floorCorner1;
    }

    public void setFloorCorner1(Block floorCorner1) {
        this.floorCorner1 = floorCorner1;
    }

    public Block getFloorCorner2() {
        return floorCorner2;
    }

    public void setFloorCorner2(Block floorCorner2) {
        this.floorCorner2 = floorCorner2;
    }

    public Block getPlaceAreaCorner1() {
        return placeAreaCorner1;
    }

    public void setPlaceAreaCorner1(Block placeAreaCorner1) {
        this.placeAreaCorner1 = placeAreaCorner1;
    }

    public Block getPlaceAreaCorner2() {
        return placeAreaCorner2;
    }

    public void setPlaceAreaCorner2(Block placeAreaCorner2) {
        this.placeAreaCorner2 = placeAreaCorner2;
    }

    public Block getWinAreaCorner1() {
        return winAreaCorner1;
    }

    public void setWinAreaCorner1(Block winAreaCorner1) {
        this.winAreaCorner1 = winAreaCorner1;
    }

    public Block getWinAreaCorner2() {
        return winAreaCorner2;
    }

    public void setWinAreaCorner2(Block winAreaCorner2) {
        this.winAreaCorner2 = winAreaCorner2;
    }

    public void finalizeSetup() {
        TNTChallenge plugin = TNTChallenge.getPlugin();
        RegionUtility regions = plugin.getRegions();
        Block wall1Corner1 = getWall1Corner1();
        Block wall1Corner2 = getWall1Corner2();
        regions.setWall1(new BoundingBox(wall1Corner1.getX(), wall1Corner1.getY(), wall1Corner1.getZ(), wall1Corner2.getX(), wall1Corner2.getY(), wall1Corner2.getZ()));
        Block wall2Corner1 = getWall2Corner1();
        Block wall2Corner2 = getWall2Corner2();
        regions.setWall2(new BoundingBox(wall2Corner1.getX(), wall2Corner1.getY(), wall2Corner1.getZ(), wall2Corner2.getX(), wall2Corner2.getY(), wall2Corner2.getZ()));
        Block wall3Corner1 = getWall3Corner1();
        Block wall3Corner2 = getWall3Corner2();
        regions.setWall3(new BoundingBox(wall3Corner1.getX(), wall3Corner1.getY(), wall3Corner1.getZ(), wall3Corner2.getX(), wall3Corner2.getY(), wall3Corner2.getZ()));
        Block wall4Corner1 = getWall4Corner1();
        Block wall4Corner2 = getWall4Corner2();
        regions.setWall4(new BoundingBox(wall4Corner1.getX(), wall4Corner1.getY(), wall4Corner1.getZ(), wall4Corner2.getX(), wall4Corner2.getY(), wall4Corner2.getZ()));
        Block floorCorner1 = getFloorCorner1();
        Block floorCorner2 = getFloorCorner2();
        regions.setFloor(new BoundingBox(floorCorner1.getX(), floorCorner1.getY(), floorCorner1.getZ(), floorCorner2.getX(), floorCorner2.getY(), floorCorner2.getZ()));
        Block placeAreaCorner1 = getPlaceAreaCorner1();
        Block placeAreaCorner2 = getPlaceAreaCorner2();
        regions.setPlaceArea(new BoundingBox(placeAreaCorner1.getX(), placeAreaCorner1.getY(), placeAreaCorner1.getZ(), placeAreaCorner2.getX(), placeAreaCorner2.getY(), placeAreaCorner2.getZ()));
        Block winAreaCorner1 = getWinAreaCorner1();
        Block winAreaCorner2 = getWinAreaCorner2();
        regions.setWinArea(new BoundingBox(winAreaCorner1.getX(), winAreaCorner1.getY(), winAreaCorner1.getZ(), winAreaCorner2.getX(), winAreaCorner2.getY(), winAreaCorner2.getZ()));
        regions.setSetup(true);
    }

    public static void leaveSetup(Player p) {
        TNTChallenge plugin = TNTChallenge.getPlugin();
        PersistentDataContainer playerData = p.getPersistentDataContainer();
        playerData.remove(new NamespacedKey(plugin, "is-setup"));
        playerData.remove(new NamespacedKey(plugin, "current-set"));
        for (ItemStack item : p.getInventory()) {
            if (item == null || item.getItemMeta() == null) continue;
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.BOLD + "SETUP STICK"))
                p.getInventory().remove(item);
        }
        p.sendMessage(ChatColor.GREEN + "[TNT Challenge] You have successfully left setup mode!");
    }

    public static void enterSetup(Player p) {
        TNTChallenge plugin = TNTChallenge.getPlugin();
        PersistentDataContainer playerData = p.getPersistentDataContainer();

        // Give the player a setup stick
        ItemStack setupStick = new ItemStack(Material.STICK);
        ItemMeta setupStickMeta = setupStick.getItemMeta();
        setupStickMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "SETUP STICK");
        setupStickMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        setupStickMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setupStick.setItemMeta(setupStickMeta);
        p.getInventory().setItemInMainHand(setupStick);

        // Put them in setup mode
        playerData.set(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN, true);
        playerData.set(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING, "wall1Corner1");

        // Initiate setup
        p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ==============");
        p.sendMessage(ChatColor.YELLOW + "Welcome to setup mode!");
        p.sendMessage(ChatColor.YELLOW + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 1 CORNER 1");
        p.sendMessage(ChatColor.YELLOW + "Please right click the first corner to continue.");
        p.sendMessage(ChatColor.YELLOW + "At any point during setup, you can use " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup cancel" + ChatColor.YELLOW + " to leave setup mode.");
        p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
    }

    public static boolean isInSetup(Player p) {
        TNTChallenge plugin = TNTChallenge.getPlugin();
        PersistentDataContainer playerData = p.getPersistentDataContainer();

        if (!playerData.has(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN))
            playerData.set(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN, false);

        return playerData.get(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN);
    }
}