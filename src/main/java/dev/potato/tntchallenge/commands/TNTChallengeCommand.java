package dev.potato.tntchallenge.commands;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.PlayerSetupRegionUtility;
import dev.potato.tntchallenge.utilities.RegionUtility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TNTChallengeCommand implements TabExecutor {
    private TNTChallenge plugin = TNTChallenge.getPlugin();
    private boolean isTikTokConnected = plugin.getConfig().isSet("tiktok-username");

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
                if (args[0].equalsIgnoreCase("help")) help(p);
            } else {
                help(p);
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
            arguments.add("help");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("setup")) arguments.add("cancel");
        return arguments;
    }

    private void help(Player p) {
        // Check for Tiktok live connectivity
        if (isTikTokConnected) {
            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "========= TNT CHALLENGE =========");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge setup [cancel]" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Allows you to configure the play area regions for the plugin");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge resetregions" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Clears all currently saved regions");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge start" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Starts the game, allowing users to trigger TNT spawns on gift");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge stop" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Stops the game, disabling TNT spawning on gift and clearing any progress made");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge pause" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Pauses the game, disabling TNT spawns on gift and the ability to trigger the countdown");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge resume" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Resumes all functions and allows viewers to spawn TNT again");
            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "================================");
        } else {
            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "========= TNT CHALLENGE =========");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge setup [cancel]" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Allows you to configure the play area regions for the plugin");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge resetregions" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Clears all currently saved regions");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge start" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Starts the game");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge stop" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Stops the game");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge pause" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Pauses the game, disabling TNT spawns and the ability to trigger the countdown");
            p.sendMessage(ChatColor.YELLOW + "/tntchallenge resume" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Resumes all functions and allows you to play again from where you left off");
            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "================================");
        }
    }

    private void setup(Player p, String[] args) {
        // Check if regions have already been configured
        if (plugin.getRegions().isSetup()) {
            p.sendMessage(ChatColor.YELLOW + "[TNT Challenge] Regions have already been configured!");
            p.sendMessage(ChatColor.YELLOW + "[TNT Challenge] If you'd like to reset your regions, please use the " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge resetregions" + ChatColor.YELLOW + " command!");
            return;
        }

        boolean isInSetup = PlayerSetupRegionUtility.isInSetup(p);

        // Check if they want to leave setup mode
        if (args.length == 2 && args[1].equalsIgnoreCase("cancel")) {
            // Check if they're currently in setup mode
            if (isInSetup) {
                // Leave setup mode
                PlayerSetupRegionUtility.leaveSetup(p);
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] You are not currently in setup mode!");
            }
            return;
        }

        // Check if they want to start setup
        if (args.length == 1) {
            // Check if they aren't already in setup mode
            if (!isInSetup) {
                // Enter setup mode
                PlayerSetupRegionUtility.enterSetup(p);
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] You are already in setup mode!");
            }
        } else {
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Incorrect command usage! Please try again.");
            p.sendMessage(ChatColor.RED + "[TNT Challenge] Example: /tntchallenge setup [cancel]");
        }
    }

    private void resetRegions(Player p) {
        // Check for Tiktok live connectivity
        if (isTikTokConnected) {
            // Check if the game is going
            if (!plugin.getTiktokGameUtility(p).isPlaying()) {
                // Reset regions
                plugin.setRegions(new RegionUtility());
                p.sendMessage(ChatColor.GREEN + "[TNT Challenge] All previously defined regions have been reset! You can now do " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup" + ChatColor.GREEN + " to set them again.");
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] The game is currently going! Regions can't be reset at this time.");
            }
        } else {
            // Check if the game is going
            if (!plugin.getSingeplayerGameUtility(p).isPlaying()) {
                // Reset regions
                plugin.setRegions(new RegionUtility());
                p.sendMessage(ChatColor.GREEN + "[TNT Challenge] All previously defined regions have been reset! You can now do " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup" + ChatColor.GREEN + " to set them again.");
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] A game is currently going! Regions can't be reset at this time.");
            }
        }
    }

    private void start(Player p, String[] args) {
        // Check for Tiktok live connectivity
        if (isTikTokConnected) {
            // Check if the game is going
            if (plugin.getTiktokGameUtility(p).isPlaying()) {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] There is already a game currently going!");
                return;
            }

            if (args.length == 1) {
                // Check if the regions are configured
                if (plugin.getRegions().isSetup()) {
                    // Start the game
                    plugin.getTiktokGameUtility(p).startGame();
                } else {
                    p.sendMessage(ChatColor.RED + "[TNT Challenge] You have not yet defined the proper regions! Please use " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup" + ChatColor.RED + " first.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] Incorrect command usage! Please try again.");
                p.sendMessage(ChatColor.RED + "[TNT Challenge] Example: /tntchallenge start");
            }
        } else {
            // Check if the game is going
            if (plugin.getSingeplayerGameUtility(p).isPlaying()) {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] There is already a game currently going!");
                return;
            }

            if (args.length == 1) {
                // Check if the regions are configured
                if (plugin.getRegions().isSetup()) {
                    // Start the game
                    plugin.getSingeplayerGameUtility(p).startGame();
                } else {
                    p.sendMessage(ChatColor.RED + "[TNT Challenge] You have not yet defined the proper regions! Please use " + ChatColor.WHITE + ChatColor.BOLD + "/tntchallenge setup" + ChatColor.RED + " first.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] Incorrect command usage! Please try again.");
                p.sendMessage(ChatColor.RED + "[TNT Challenge] Example: /tntchallenge start");
            }
        }
    }

    private void stop(Player p, String[] args) {
        // Check for Tiktok live connectivity
        if (isTikTokConnected) {
            if (args.length == 1) {
                // Check if the game is going
                if (plugin.getTiktokGameUtility(p).isPlaying()) {
                    // End the game
                    plugin.getTiktokGameUtility(p).endGame();
                } else {
                    p.sendMessage(ChatColor.RED + "[TNT Challenge] The game is not currently going!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] Incorrect command usage! Please try again.");
                p.sendMessage(ChatColor.RED + "[TNT Challenge] Example: /tntchallenge stop");
            }
        } else {
            if (args.length == 1) {
                // Check if the game is going
                if (plugin.getSingeplayerGameUtility(p).isPlaying()) {
                    // End the game
                    plugin.getSingeplayerGameUtility(p).endGame();
                } else {
                    p.sendMessage(ChatColor.RED + "[TNT Challenge] There is no game currently going!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] Incorrect command usage! Please try again.");
                p.sendMessage(ChatColor.RED + "[TNT Challenge] Example: /tntchallenge stop");
            }
        }
    }

    private void pause(Player p) {
        // Check for Tiktok live connectivity
        if (isTikTokConnected) {
            // Check if the game is going
            if (plugin.getTiktokGameUtility(p).isPlaying()) {
                // Pause the game
                plugin.getTiktokGameUtility(p).setPaused(true);
                p.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "Game is now paused!", ChatColor.WHITE + "" + ChatColor.BOLD + "Use the resume command to continue!", 2, 60, 2);
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] The game is not currently going!");
            }
        } else {
            // Check if the game is going
            if (plugin.getSingeplayerGameUtility(p).isPlaying()) {
                // Pause the game
                plugin.getSingeplayerGameUtility(p).setPaused(true);
                p.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "Game is now paused!", ChatColor.WHITE + "" + ChatColor.BOLD + "Use the resume command to continue!", 2, 60, 2);
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] There is no game currently going!");
            }
        }
    }

    private void resume(Player p) {
        // Check for Tiktok live connectivity
        if (isTikTokConnected) {
            // Check if the game is going
            if (plugin.getTiktokGameUtility(p).isPlaying()) {
                // Check if the game is paused
                if (plugin.getTiktokGameUtility(p).isPaused()) {
                    // Resume the game
                    plugin.getTiktokGameUtility(p).setPaused(false);
                    p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Game is now resumed!", ChatColor.WHITE + "" + ChatColor.BOLD + "Use the pause command to pause at any time!", 2, 60, 2);
                } else {
                    p.sendMessage(ChatColor.RED + "[TNT Challenge] The game is already going!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] The game is not currently going!");
            }
        } else {
            // Check if the game is going
            if (plugin.getSingeplayerGameUtility(p).isPlaying()) {
                // Check if the game is paused
                if (plugin.getSingeplayerGameUtility(p).isPaused()) {
                    // Resume the game
                    plugin.getSingeplayerGameUtility(p).setPaused(false);
                    p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Game is now resumed!", ChatColor.WHITE + "" + ChatColor.BOLD + "Use the pause command to pause at any time!", 2, 60, 2);
                } else {
                    p.sendMessage(ChatColor.RED + "[TNT Challenge] The game is already going!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "[TNT Challenge] There is no game currently going!");
            }
        }
    }
}