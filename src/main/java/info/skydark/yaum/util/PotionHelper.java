package info.skydark.yaum.util;

import net.minecraft.potion.Potion;

/**
 * Created by skydark on 16-1-8.
 */
public class PotionHelper {
    public static Potion getPotionByName(String name) {
        for (Potion potion : Potion.potionTypes) {
            if (potion != null && potion.getName().equals(name)) {
                return potion;
            }
        }
        return null;
    }
}
