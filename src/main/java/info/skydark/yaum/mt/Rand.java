package info.skydark.yaum.mt;

import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Random;

/**
 * Created by skydark on 15-11-18.
 */
@ZenClass("mod.yaum.Rand")
public class Rand {
    private static Random rand = new Random();

    @ZenMethod
    public static int nextInt(@Optional int n) {
        return n == 0 ? rand.nextInt() : rand.nextInt(n);
    }

    @ZenMethod
    public static float nextFloat() {
        return rand.nextFloat();
    }
}