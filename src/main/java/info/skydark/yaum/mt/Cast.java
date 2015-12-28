package info.skydark.yaum.mt;

import info.skydark.yaum.Config;
import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-11-14.
 */
@ZenClass("mod.yaum.Cast")
public class Cast {
    @ZenMethod
    public static int toInt(IData data, @Optional int _default) {
        try {
            return data.asInt();
        } catch (Exception e) {
            if (Config.debugMode) e.printStackTrace();
            return _default;
        }
    }

    @ZenMethod
    public static boolean toBool(IData data, @Optional boolean _default) {
        try {
            return data.asBool();
        } catch (Exception e) {
            if (Config.debugMode) e.printStackTrace();
            return _default;
        }
    }

    @ZenMethod
    public static String toString(IData data, @Optional String _default) {
        try {
            return data.asString();
        } catch (Exception e) {
            if (Config.debugMode) e.printStackTrace();
            return _default;
        }
    }

    @ZenMethod
    public static boolean equals(IData data1, IData data2) {
        if (data1 == null) {
            return data2 == null;
        }
        try {
            return data1.equals(data2);
        } catch (Exception e) {
            if (Config.debugMode) e.printStackTrace();
            return false;
        }
    }

    @ZenMethod
    public static boolean isNull(String data1) {
        return data1 == null;
    }

    @ZenMethod
    public static boolean isNull(IData data1) {
        return data1 == null;
    }

    @ZenMethod
    public static boolean notNull(String data1) {
        return data1 != null;
    }

    @ZenMethod
    public static boolean notNull(IData data1) {
        return data1 != null;
    }
}
