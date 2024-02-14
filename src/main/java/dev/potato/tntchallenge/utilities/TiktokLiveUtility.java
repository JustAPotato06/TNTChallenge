package dev.potato.tntchallenge.utilities;

import dev.potato.tntchallenge.TNTChallenge;
import dev.potato.tntchallenge.tasks.TiktokHelpBuildTask;
import dev.potato.tntchallenge.tasks.TiktokTNTSpawnTask;
import io.github.jwdeveloper.tiktok.TikTokLive;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TiktokLiveUtility {
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static void init() {
        FileConfiguration config = TNTChallenge.getPlugin().getConfig();
        TikTokLive.newClient(config.getString("tiktok-username"))
                .onGift((liveClient, tikTokGiftEvent) -> {
                    TNTChallenge plugin = TNTChallenge.getPlugin();
                    Player p = plugin.getTiktokLiveUtility().getPlayer();
                    if (p != null) {
                        if (plugin.getTiktokGameUtility(p).isPlaying()) {
                            if (!plugin.getTiktokGameUtility(p).isPaused()) {
                                int amountSent = tikTokGiftEvent.getCombo();
                                switch (tikTokGiftEvent.getGift()) {
                                    case ROSE -> {
                                        new TiktokTNTSpawnTask(amountSent, 2, 0, "x" + amountSent + " TNT!", p, tikTokGiftEvent, 0, 40)
                                                .runTaskTimer(plugin, 0, 1);
                                    }
                                    case COFFEE -> {
                                        int amountOfTNT = amountSent * 10;
                                        new TiktokTNTSpawnTask(amountOfTNT, 2, 0, "x" + amountOfTNT + " TNT!", p, tikTokGiftEvent, 0, 40)
                                                .runTaskTimer(plugin, 0, 1);
                                    }
                                    case PERFUME -> {
                                        int amountOfTNT = amountSent * 20;
                                        new TiktokTNTSpawnTask(amountOfTNT, 2, 0, "x" + amountOfTNT + " TNT!", p, tikTokGiftEvent, 0, 40)
                                                .runTaskTimer(plugin, 0, 1);
                                    }
                                    case DOUGHNUT -> {
                                        int amountOfTNT = amountSent * 30;
                                        new TiktokTNTSpawnTask(amountOfTNT, 2, 0, "x" + amountOfTNT + " TNT!", p, tikTokGiftEvent, 0, 40)
                                                .runTaskTimer(plugin, 0, 1);
                                    }
                                    case HAND_HEARTS -> {
                                        int amountOfTNT = amountSent * 50;
                                        new TiktokTNTSpawnTask(amountOfTNT, 2, 0, "x" + amountOfTNT + " TNT!", p, tikTokGiftEvent, 0, 40)
                                                .runTaskTimer(plugin, 0, 1);
                                    }
                                    case CORGI -> {
                                        int amountOfTNT = amountSent * 50;
                                        new TiktokTNTSpawnTask(amountOfTNT, 5, 0, "TNT RAIN!", p, tikTokGiftEvent, 15, 80)
                                                .runTaskTimer(plugin, 0, 5);
                                    }
                                    case SWAN -> {
                                        int amountOfTNT = amountSent * 800;
                                        new TiktokTNTSpawnTask(amountOfTNT, 5, -2, "x" + amountOfTNT + " TNT AND -2 WINS!", p, tikTokGiftEvent, 0, 40)
                                                .runTaskTimer(plugin, 0, 1);
                                    }
                                    case GALAXY -> {
                                        int amountOfTNT = amountSent * 3000;
                                        new TiktokTNTSpawnTask(amountOfTNT, 5, -5, "x" + amountOfTNT + " TNT AND -5 WINS!", p, tikTokGiftEvent, 0, 40)
                                                .runTaskTimer(plugin, 0, 1);
                                    }
                                    case CONFETTI -> {
                                        new TiktokHelpBuildTask(p, tikTokGiftEvent).runTask(plugin);
                                    }
                                    case MONEY_GUN -> {
                                        int amountOfTNT = amountSent * 200;
                                        new TiktokTNTSpawnTask(amountOfTNT, 5, 0, "A TOTAL RESET!", p, tikTokGiftEvent, 0, 40)
                                                .runTaskTimer(plugin, 0, 1);
                                    }
                                }
                            }
                        }
                    }
                })
                .onConnected((liveClient, tikTokConnectedEvent) -> {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[TNT Challenge] A Tiktok live has been found! TNT Challenge was connected successfully.");
                })
                .onError((liveClient, tikTokErrorEvent) -> {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[TNT Challenge] There was an error communicating with the Tiktok live!");
                })
                .buildAndConnect();
    }
}