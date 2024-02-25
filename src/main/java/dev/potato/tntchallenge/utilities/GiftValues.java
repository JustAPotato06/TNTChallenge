package dev.potato.tntchallenge.utilities;

import dev.potato.tntchallenge.TNTChallenge;
import org.bukkit.configuration.file.FileConfiguration;

public class GiftValues {
    private static FileConfiguration config = TNTChallenge.getPlugin().getConfig();

    // Rose
    public static final int TNT_AMOUNT_PER_ROSE = config.getInt("rose.tnt-amount-per-gift");
    public static final int ROSE_TNT_POWER_LEVEL = config.getInt("rose.tnt-power-level");
    public static final int ADDED_WINS_PER_ROSE = config.getInt("rose.added-wins-per-gift");
    public static final String ROSE_GIFT_MESSAGE = config.getString("rose.gift-message");
    public static final int ROSE_AMOUNT_ADDED_TO_Y = config.getInt("rose.amount-added-to-tnt-y");
    public static final int ROSE_TNT_FUSE_TICKS = config.getInt("rose.tnt-fuse-ticks");
    public static final int ROSE_TIME_BETWEEN_TNT = config.getInt("rose.time-between-tnt");

    // Coffee
    public static final int TNT_AMOUNT_PER_COFFEE = config.getInt("coffee.tnt-amount-per-gift");
    public static final int COFFEE_TNT_POWER_LEVEL = config.getInt("coffee.tnt-power-level");
    public static final int ADDED_WINS_PER_COFFEE = config.getInt("coffee.added-wins-per-gift");
    public static final String COFFEE_GIFT_MESSAGE = config.getString("coffee.gift-message");
    public static final int COFFEE_AMOUNT_ADDED_TO_Y = config.getInt("coffee.amount-added-to-tnt-y");
    public static final int COFFEE_TNT_FUSE_TICKS = config.getInt("coffee.tnt-fuse-ticks");
    public static final int COFFEE_TIME_BETWEEN_TNT = config.getInt("coffee.time-between-tnt");

    // Perfume
    public static final int TNT_AMOUNT_PER_PERFUME = config.getInt("perfume.tnt-amount-per-gift");
    public static final int PERFUME_TNT_POWER_LEVEL = config.getInt("perfume.tnt-power-level");
    public static final int ADDED_WINS_PER_PERFUME = config.getInt("perfume.added-wins-per-gift");
    public static final String PERFUME_GIFT_MESSAGE = config.getString("perfume.gift-message");
    public static final int PERFUME_AMOUNT_ADDED_TO_Y = config.getInt("perfume.amount-added-to-tnt-y");
    public static final int PERFUME_TNT_FUSE_TICKS = config.getInt("perfume.tnt-fuse-ticks");
    public static final int PERFUME_TIME_BETWEEN_TNT = config.getInt("perfume.time-between-tnt");

    // Doughnut
    public static final int TNT_AMOUNT_PER_DOUGHNUT = config.getInt("doughnut.tnt-amount-per-gift");
    public static final int DOUGHNUT_TNT_POWER_LEVEL = config.getInt("doughnut.tnt-power-level");
    public static final int ADDED_WINS_PER_DOUGHNUT = config.getInt("doughnut.added-wins-per-gift");
    public static final String DOUGHNUT_GIFT_MESSAGE = config.getString("doughnut.gift-message");
    public static final int DOUGHNUT_AMOUNT_ADDED_TO_Y = config.getInt("doughnut.amount-added-to-tnt-y");
    public static final int DOUGHNUT_TNT_FUSE_TICKS = config.getInt("doughnut.tnt-fuse-ticks");
    public static final int DOUGHNUT_TIME_BETWEEN_TNT = config.getInt("doughnut.time-between-tnt");

    // Hand Hearts
    public static final int TNT_AMOUNT_PER_HAND_HEARTS = config.getInt("hand-hearts.tnt-amount-per-gift");
    public static final int HAND_HEARTS_TNT_POWER_LEVEL = config.getInt("hand-hearts.tnt-power-level");
    public static final int ADDED_WINS_PER_HAND_HEARTS = config.getInt("hand-hearts.added-wins-per-gift");
    public static final String HAND_HEARTS_GIFT_MESSAGE = config.getString("hand-hearts.gift-message");
    public static final int HAND_HEARTS_AMOUNT_ADDED_TO_Y = config.getInt("hand-hearts.amount-added-to-tnt-y");
    public static final int HAND_HEARTS_TNT_FUSE_TICKS = config.getInt("hand-hearts.tnt-fuse-ticks");
    public static final int HAND_HEARTS_TIME_BETWEEN_TNT = config.getInt("hand-hearts.time-between-tnt");

    // Corgi
    public static final int TNT_AMOUNT_PER_CORGI = config.getInt("corgi.tnt-amount-per-gift");
    public static final int CORGI_TNT_POWER_LEVEL = config.getInt("corgi.tnt-power-level");
    public static final int ADDED_WINS_PER_CORGI = config.getInt("corgi.added-wins-per-gift");
    public static final String CORGI_GIFT_MESSAGE = config.getString("corgi.gift-message");
    public static final int CORGI_AMOUNT_ADDED_TO_Y = config.getInt("corgi.amount-added-to-tnt-y");
    public static final int CORGI_TNT_FUSE_TICKS = config.getInt("corgi.tnt-fuse-ticks");
    public static final int CORGI_TIME_BETWEEN_TNT = config.getInt("corgi.time-between-tnt");

    // Swan
    public static final int TNT_AMOUNT_PER_SWAN = config.getInt("swan.tnt-amount-per-gift");
    public static final int SWAN_TNT_POWER_LEVEL = config.getInt("swan.tnt-power-level");
    public static final int ADDED_WINS_PER_SWAN = config.getInt("swan.added-wins-per-gift");
    public static final String SWAN_GIFT_MESSAGE = config.getString("swan.gift-message");
    public static final int SWAN_AMOUNT_ADDED_TO_Y = config.getInt("swan.amount-added-to-tnt-y");
    public static final int SWAN_TNT_FUSE_TICKS = config.getInt("swan.tnt-fuse-ticks");
    public static final int SWAN_TIME_BETWEEN_TNT = config.getInt("swan.time-between-tnt");

    // Galaxy
    public static final int TNT_AMOUNT_PER_GALAXY = config.getInt("galaxy.tnt-amount-per-gift");
    public static final int GALAXY_TNT_POWER_LEVEL = config.getInt("galaxy.tnt-power-level");
    public static final int ADDED_WINS_PER_GALAXY = config.getInt("galaxy.added-wins-per-gift");
    public static final String GALAXY_GIFT_MESSAGE = config.getString("galaxy.gift-message");
    public static final int GALAXY_AMOUNT_ADDED_TO_Y = config.getInt("galaxy.amount-added-to-tnt-y");
    public static final int GALAXY_TNT_FUSE_TICKS = config.getInt("galaxy.tnt-fuse-ticks");
    public static final int GALAXY_TIME_BETWEEN_TNT = config.getInt("galaxy.time-between-tnt");

    // Confetti
    public static final boolean CONFETTI_SHOULD_HELP = config.getBoolean("confetti.should-help");

    // Money Gun
    public static final int TNT_AMOUNT_PER_MONEY_GUN = config.getInt("money-gun.tnt-amount-per-gift");
    public static final int MONEY_GUN_TNT_POWER_LEVEL = config.getInt("money-gun.tnt-power-level");
    public static final int ADDED_WINS_PER_MONEY_GUN = config.getInt("money-gun.added-wins-per-gift");
    public static final String MONEY_GUN_GIFT_MESSAGE = config.getString("money-gun.gift-message");
    public static final int MONEY_GUN_AMOUNT_ADDED_TO_Y = config.getInt("money-gun.amount-added-to-tnt-y");
    public static final int MONEY_GUN_TNT_FUSE_TICKS = config.getInt("money-gun.tnt-fuse-ticks");
    public static final int MONEY_GUN_TIME_BETWEEN_TNT = config.getInt("money-gun.time-between-tnt");
}