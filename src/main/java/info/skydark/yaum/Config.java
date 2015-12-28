package info.skydark.yaum;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by skydark on 15-8-28.
 */
public final class Config {
    public static Configuration config;
    private static final String c_general = Configuration.CATEGORY_GENERAL;
    private static final String c_disable = "disable";

    public static boolean isStatItemDisabled;
    public static boolean isOnceSpawnerDisabled;
    public static boolean mtIntegration;
    public static boolean debugMode;
    public static String tooltipMatcher;
    public static boolean dispenserFillCauldron;

    public static void init(File configFile) {
        config = new Configuration(configFile);
        config.load();

        mtIntegration = loadBoolean(c_general, "mtIntegration", true, "add minetweaker integration");
        debugMode = loadBoolean(c_general, "DebugMode", false, "debug mode");
        isStatItemDisabled = loadBoolean(c_disable, "StatItem", false, "disable StatItem");
        isOnceSpawnerDisabled = loadBoolean(c_disable, "OnceSpawner", false, "disable OnceSpawner");
        tooltipMatcher = loadString(c_general, "tooltipMatcher", "\\$\\{([^}]*)\\}", "regexp to translate tooltips, empty to disable");
        dispenserFillCauldron = loadBoolean(c_general, "dispenserFillCauldron", true, "water bucket in dispenser can fill cauldron");

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static int loadInt(String category, String name, int _default, String comment) {
        return config.get(category, name, _default, comment).getInt(_default);
    }

    public static boolean loadBoolean(String category, String name, boolean _default, String comment) {
        return config.get(category, name, _default, comment).getBoolean(_default);
    }

    public static String loadString(String category, String name, String _default, String comment) {
        return config.get(category, name, _default, comment).getString();
    }
}
