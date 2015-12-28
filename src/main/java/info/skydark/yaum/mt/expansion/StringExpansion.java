package info.skydark.yaum.mt.expansion;

import info.skydark.yaum.mt.MTHelper;
import minetweaker.api.entity.IEntity;
import net.minecraft.util.StatCollector;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-11-14.
 */
@ZenExpansion("string")
public class StringExpansion {
    @ZenMethod
    public static boolean equals(String string1, String string2) {
        return string1.equals(string2);
    }

    @ZenMethod
    public static boolean equalsIgnoreCase(String string1, String string2) {
        return string1.equalsIgnoreCase(string2);
    }

    @ZenMethod
    public static boolean matchEntity(String pattern, IEntity iEntity) {
        return MTHelper.matchEntity(pattern, iEntity);
    }

    @ZenMethod
    public static String localize(String message) {
        return StatCollector.translateToLocal(message);
    }
}
