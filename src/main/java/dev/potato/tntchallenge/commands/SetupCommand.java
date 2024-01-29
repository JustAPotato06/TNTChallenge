package dev.potato.tntchallenge.commands;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.utilities.RegionPlayerUtilities;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SetupCommand extends SubCommand {
    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("s");
        return aliases;
    }

    @Override
    public String getDescription() {
        return "The setup command for the TNT Challenge plugin";
    }

    @Override
    public String getSyntax() {
        return "/tntchallenge setup [cancel]";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            PersistentDataContainer pData = p.getPersistentDataContainer();
            if (args.length == 2 && args[1].equalsIgnoreCase("cancel")) {
                if (pData.get(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN)) {
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN, false);
                    TNTChallenge.getPlugin().getSetupUtilities().remove(p);
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[TNT Challenge] You have successfully left setup mode!");
                } else {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] You are not currently in setup mode!");
                }
            } else if (args.length == 1) {
                if (!pData.get(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN)) {
                    pData.set(new NamespacedKey(TNTChallenge.getPlugin(), "is-setup"), PersistentDataType.BOOLEAN, true);
                    TNTChallenge.getPlugin().getSetupUtilities().put(p, new RegionPlayerUtilities());
                    RegionPlayerUtilities rpu = TNTChallenge.getPlugin().getSetupUtilities().get(p);
                    // Logic for setting region bounds
                } else {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] You are already in setup mode!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] Incorrect command usage! Please try again.");
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[TNT Challenge] Example: " + getSyntax());
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 2) {
            arguments.add("cancel");
        }
        return arguments;
    }
}
