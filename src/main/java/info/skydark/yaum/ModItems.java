package info.skydark.yaum;

import info.skydark.yaum.item.StatItem;
import net.minecraft.item.Item;

/**
 * Created by skydark on 15-8-26.
 */
public final class ModItems {
    public static Item statItem;

    public static void init() {
        if (!Config.isStatItemDisabled) {
            statItem = new StatItem();
        }
    }
}
