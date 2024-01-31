package dev.potato.tntchallenge.commands;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.GameUtilities;
import dev.potato.tntchallenge.utilities.PlayerSetupRegionUtilities;
import dev.potato.tntchallenge.utilities.RegionUtilities;
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
    private TNTChallenge plugin = TNTChallenge.getPlugin();
    private HashMap<Player, PlayerSetupRegionUtilities> setupUtilities = plugin.getSetupUtilities();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("setup")) setup(p, args);
                if (args[0].equalsIgnoreCase("resetregions")) resetRegions(p);
                if (args[0].equalsIgnoreCase("start")) start(p, args);
                if (args[0].equalsIgnoreCase("stop")) stop(p, args);
                if (args[0].equalsIgnoreCase("pause")) pause(p);
                if (args[0].equalsIgnoreCase("resume")) resume(p);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 1) {
            arguments.add("setup");
            arguments.add("resetregions");
            arguments.add("start");
            arguments.add("stop");
            arguments.add("pause");
            arguments.add("resume");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("setup")) arguments.add("cancel");
        if (args.length == 2 && args[0].equalsIgnoreCase("start")) arguments.add("usetiktok");
        if (args.length == 2 && args[0].equalsIgnoreCase("stop")) arguments.add("tiktok");
        return arguments;
    }

    private void setup(Player p, String[] args) {
        // Check if regions have already been configured
        if (plugin.getRegions().isSetup()) {
            p.sendMessage(ChatColor.YELLOW + "[TNT Challenge] Regions have already been configured!");
            p.sendMessage(ChatColor.YELLOW + "[TNT Challenge] If you'd like to reset your regions, please use the " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge resetregions" + ChatColor.YELLOW + " command!");
            return;
        }
        // Make sure if the player doesn't have a setup boolean, change it to false
        PersistentDataContainer pData = p.getPersistentDataContainer();
        if (!pData.has(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN)) {
            pData.set(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN, false);
        }
        // Check if they want to leave setup mode
        if (args.length == 2 && args[1].equalsIgnoreCase("cancel")) {
            // Check if they're currently in setup mode
            if (pData.get(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN)) {
                // Leave setup mode
                pData.set(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN, false);
                setupUtilities.remove(p);
                p.sendMessage(ChatColor.GREEN + "[TNT Challenge] You have successfully left setup mode!");
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] You are not currently in setup mode!");
            }
            return;
        }
        // Check if they want to start setup
        if (args.length == 1) {
            // Check if they aren't already in setup mode
            if (!pData.get(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN)) {
                // Check if the item in their hand is a stick
                if (p.getInventory().getItemInMainHand().getType() == Material.STICK) {
                    // Put them in setup mode
                    pData.set(new NamespacedKey(plugin, "is-setup"), PersistentDataType.BOOLEAN, true);
                    setupUtilities.put(p, new PlayerSetupRegionUtilities());
                    // Initiate setup
                    pData.set(new NamespacedKey(plugin, "current-set"), PersistentDataType.STRING, "wall1Corner1");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "============ " + ChatColor.WHITE + ChatColor.BOLD + "TNT Challenge" + ChatColor.GOLD + ChatColor.BOLD + " ==============");
                    p.sendMessage(ChatColor.YELLOW + "Welcome to setup mode!");
                    p.sendMessage(ChatColor.YELLOW + "You are now configuring " + ChatColor.WHITE + ChatColor.BOLD + "WALL 1 CORNER 1");
                    p.sendMessage(ChatColor.YELLOW + "Please right click the first corner to continue.");
                    p.sendMessage(ChatColor.YELLOW + "At any point during setup, you can use " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup cancel" + ChatColor.YELLOW + " to leave setup mode.");
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "=======================================");
                } else {
                    p.sendMessage(ChatColor.RED + "[TNT Challenge] You must be holding a stick to enter setup mode!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] You are already in setup mode!");
            }
        } else {
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Incorrect command usage! Please try again.");
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Example: /tntchallenge setup [cancel]");
        }
    }

    private void resetRegions(Player p) {
        plugin.setRegions(new RegionUtilities());
        plugin.getSetupUtilities().remove(p);
        p.sendMessage(ChatColor.GREEN + "[TNT Challenge] All previously defined regions have been reset! You can now do " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup" + ChatColor.GREEN + " to set them again.");
    }

    private void start(Player p, String[] args) {
        // Check if they want to use Tiktok connectivity
        if (args.length == 2 && args[1].equalsIgnoreCase("usetiktok")) {
            // Tiktok Game logic
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Tiktok livestream compatability has not yet been set up! It's expected to be added in a future update.");
        } else if (args.length == 1) {
            // Check if the regions are configured
            if (plugin.getRegions().isSetup()) {
                // Start the game
                GameUtilities.startGame(p);
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] You have not yet defined the proper regions! Please use " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup" + ChatColor.RED + " first.");
            }
        } else {
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Incorrect command usage! Please try again.");
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Example: /tntchallenge start [usetiktok]");
        }
    }

    private void stop(Player p, String[] args) {
        // Check if they are using Tiktok connectivity
        if (args.length == 2 && args[1].equalsIgnoreCase("tiktok")) {
            // Tiktok Game logic
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Tiktok livestream compatability has not yet been set up! It's expected to be added in a future update.");
        } else if (args.length == 1) {
            // Check if the game is going
            if (plugin.getGameUtilities().isPlaying()) {
                // End the game
                GameUtilities.endGame(p);
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] There is no game currently going!");
            }
        } else {
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Incorrect command usage! Please try again.");
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Example: /tntchallenge stop [tiktok]");
        }
    }

    private void pause(Player p) {
        // Check if the game is going
        if (plugin.getGameUtilities().isPlaying()) {
            // Pause the game
            plugin.getGameUtilities().setPaused(true);
            p.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "Game is now paused!", ChatColor.WHITE + "" + ChatColor.BOLD + "Use the resume command to continue!", 2, 60, 2);
        } else {
            p.sendMessage(ChatColor.RED + "[TNT Challenge] There is no game currently going!");
        }
    }

    private void resume(Player p) {
        // Check if the game is going
        if (plugin.getGameUtilities().isPlaying()) {
            // Check if the game is paused
            if (plugin.getGameUtilities().isPaused()) {
                // Resume the game
                plugin.getGameUtilities().setPaused(false);
                p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Game is now resumed!", ChatColor.WHITE + "" + ChatColor.BOLD + "Use the pause command to pause at any time!", 2, 60, 2);
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] The game is already going!");
            }
        } else {
            p.sendMessage(ChatColor.RED + "[TNT Challenge] There is no game currently going!");
        }
    }
}
