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
        TNTChallenge plugin = TNTChallenge.getPlugin();
        String username = config.getString("tiktok-username");

        TikTokLive.newClient(username)
                .onGift((liveClient, tikTokGiftEvent) -> {
                    Player p = plugin.getTiktokLiveUtility().getPlayer();
                    boolean shouldContinue = p != null && plugin.getTiktokGameUtility(p).isPlaying() && !plugin.getTiktokGameUtility(p).isPaused();

                    if (shouldContinue) {
                        int amountSent = tikTokGiftEvent.getCombo();
                        switch (tikTokGiftEvent.getGift()) {
                            case ROSE -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_ROSE;
                                int addedWins = GiftValues.ADDED_WINS_PER_ROSE;
                                String giftMessage = "";
                                if (GiftValues.ROSE_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.ROSE_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.ROSE_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.ROSE_AMOUNT_ADDED_TO_Y, GiftValues.ROSE_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.ROSE_TIME_BETWEEN_TNT);
                            }
                            case COFFEE -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_COFFEE;
                                int addedWins = GiftValues.ADDED_WINS_PER_COFFEE;
                                String giftMessage = "";
                                if (GiftValues.COFFEE_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.COFFEE_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.COFFEE_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.COFFEE_AMOUNT_ADDED_TO_Y, GiftValues.COFFEE_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.COFFEE_TIME_BETWEEN_TNT);
                            }
                            case PERFUME -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_PERFUME;
                                int addedWins = GiftValues.ADDED_WINS_PER_PERFUME;
                                String giftMessage = "";
                                if (GiftValues.PERFUME_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.PERFUME_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.PERFUME_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.PERFUME_AMOUNT_ADDED_TO_Y, GiftValues.PERFUME_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.PERFUME_TIME_BETWEEN_TNT);
                            }
                            case DOUGHNUT -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_DOUGHNUT;
                                int addedWins = GiftValues.ADDED_WINS_PER_DOUGHNUT;
                                String giftMessage = "";
                                if (GiftValues.DOUGHNUT_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.DOUGHNUT_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.DOUGHNUT_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.DOUGHNUT_AMOUNT_ADDED_TO_Y, GiftValues.DOUGHNUT_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.DOUGHNUT_TIME_BETWEEN_TNT);
                            }
                            case HAND_HEARTS -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_HAND_HEARTS;
                                int addedWins = GiftValues.ADDED_WINS_PER_HAND_HEARTS;
                                String giftMessage = "";
                                if (GiftValues.HAND_HEARTS_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.HAND_HEARTS_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.HAND_HEARTS_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.HAND_HEARTS_AMOUNT_ADDED_TO_Y, GiftValues.HAND_HEARTS_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.HAND_HEARTS_TIME_BETWEEN_TNT);
                            }
                            case CORGI -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_CORGI;
                                int addedWins = GiftValues.ADDED_WINS_PER_CORGI;
                                String giftMessage = "";
                                if (GiftValues.CORGI_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.CORGI_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.CORGI_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.CORGI_AMOUNT_ADDED_TO_Y, GiftValues.CORGI_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.CORGI_TIME_BETWEEN_TNT);
                            }
                            case SWAN -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_SWAN;
                                int addedWins = GiftValues.ADDED_WINS_PER_SWAN;
                                String giftMessage = "";
                                if (GiftValues.SWAN_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.SWAN_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.SWAN_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.SWAN_AMOUNT_ADDED_TO_Y, GiftValues.SWAN_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.SWAN_TIME_BETWEEN_TNT);
                            }
                            case GALAXY -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_GALAXY;
                                int addedWins = GiftValues.ADDED_WINS_PER_GALAXY;
                                String giftMessage = "";
                                if (GiftValues.GALAXY_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.GALAXY_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.GALAXY_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.GALAXY_AMOUNT_ADDED_TO_Y, GiftValues.GALAXY_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.GALAXY_TIME_BETWEEN_TNT);
                            }
                            case CONFETTI -> {
                                if (GiftValues.CONFETTI_SHOULD_HELP)
                                    new TiktokHelpBuildTask(p, tikTokGiftEvent).runTask(plugin);
                            }
                            case MONEY_GUN -> {
                                int amountOfTNT = amountSent * GiftValues.TNT_AMOUNT_PER_MONEY_GUN;
                                int addedWins = GiftValues.ADDED_WINS_PER_MONEY_GUN;
                                String giftMessage = "";
                                if (GiftValues.MONEY_GUN_GIFT_MESSAGE.equalsIgnoreCase("default"))
                                    giftMessage = "x" + amountSent + " TNT!";
                                else if (GiftValues.MONEY_GUN_GIFT_MESSAGE.equalsIgnoreCase("default-include-wins"))
                                    giftMessage = "x" + amountOfTNT + " TNT AND " + addedWins + " WINS!";
                                new TiktokTNTSpawnTask(amountOfTNT, GiftValues.MONEY_GUN_TNT_POWER_LEVEL, addedWins, giftMessage, p, tikTokGiftEvent, GiftValues.MONEY_GUN_AMOUNT_ADDED_TO_Y, GiftValues.MONEY_GUN_TNT_FUSE_TICKS).runTaskTimer(plugin, 0, GiftValues.MONEY_GUN_TIME_BETWEEN_TNT);
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