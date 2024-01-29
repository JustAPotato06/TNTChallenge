package dev.potato.tntchallenge.commands;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerRegionUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TNTChallengeCommand implements TabExecutor {
    HashMap<Player, PlayerRegionUtilities> setupUtilities = TNTChallenge.getPlugin().getSetupUtilities();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("setup")) {
                    setupCommand(p, args);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 1) {
            arguments.add("setup");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("setup")) {
            arguments.add("cancel");
        }
        return arguments;
    }

    private void setupCommand(Player p, String[] args) {
        if (TNTChallenge.getPlugin().getRegions().isSetup()) {
            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[TNT Challenge] Regions have already been configured!");
            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[TNT Challenge] If you'd like to reset your regions, please use the " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge resetregions" + ChatColor.YELLOW + ChatColor.BOLD + " command!");
            return;
        }

        PersistentDataContainer pData = p.getPersistentDataContainer();

        if (!pData.has(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN)) {
            pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN, false);
        }

        if (args.length == 2 && args[1].equalsIgnoreCase("cancel")) {
            if (pData.get(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN)) {
                pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN, false);
                setupUtilities.remove(p);
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[TNT Challenge] You have successfully left setup mode!");
            } else {
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] You are not currently in setup mode!");
            }
            return;
        }

        if (args.length == 1) {
            if (!pData.get(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN)) {
                if (p.getInventory().getItemInMainHand().getType() == Material.STICK) {
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN, true);
                    setupUtilities.put(p, new PlayerRegionUtilities());
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "current-set"), PersistentDataType.STRING, "wall1Corner1");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ============");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Welcome to setup mode! You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 1 CORNER 1");
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please right click the first corner to continue.");
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "At any point during setup, you can use " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup cancel" + ChatColor.GREEN + ChatColor.BOLD + " to leave setup mode.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] You must be holding a stick to enter setup mode!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] You are already in setup mode!");
            }
        } else {
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] Incorrect command usage! Please try again.");
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] Example: /tntchallenge setup [cancel]");
        }
    }
}
