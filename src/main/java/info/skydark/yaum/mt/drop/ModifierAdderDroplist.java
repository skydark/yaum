package info.skydark.yaum.mt.drop;

import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;

import java.util.Random;

/**
 * Created by skydark on 15-11-17.
 */
public class ModifierAdderDroplist extends ModifierAdderCommon {
    private final int min_tries;
    private final int max_tries;

    public ModifierAdderDroplist(IItemStack[] stack, int min_tries, int max_tries, int amount_min, int amount_max) {
        super(stack, 1.0f, amount_min, amount_max);
        this.min_tries = min_tries > 0 ? min_tries : 0;
        this.max_tries = max_tries > this.min_tries? max_tries : this.min_tries;
    }

    public ModifierAdderDroplist(WeightedItemStack[] stack, int min_tries, int max_tries, int amount_min, int amount_max) {
        super(stack, 1.0f, amount_min, amount_max);
        this.min_tries = min_tries > 0 ? min_tries : 0;
        this.max_tries = max_tries > this.min_tries? max_tries : this.min_tries;
    }

    @Override
    protected int getAmount(Random rand, int amount_min, int amount_max, float chance, int lootingLevel, boolean recentlyHit) {
        return amount_min + rand.nextInt(amount_max - amount_min + 1) + rand.nextInt(lootingLevel + 1);
    }

    @Override
    protected int getTries(Random rand) {
        return rand.nextInt(max_tries - min_tries + 1) + min_tries;
    }
}
