package info.skydark.yaum.mt.drop;

import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;

import java.util.Random;

/**
 * Created by skydark on 15-11-17.
 */
public class ModifierAdderCommon extends ModifierAdder {
    public ModifierAdderCommon(IItemStack stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    public ModifierAdderCommon(WeightedItemStack stack, int amount_min, int amount_max) {
        super(stack, amount_min, amount_max);
    }

    public ModifierAdderCommon(IItemStack[] stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    public ModifierAdderCommon(WeightedItemStack[] stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    @Override
    protected int getAmount(Random rand, int amount_min, int amount_max, float chance, int lootingLevel, boolean recentlyHit) {
        return super.getAmount(rand, amount_min, amount_max, chance, 0, recentlyHit) +
                super.getAmount(rand, 0, lootingLevel, chance, 0, recentlyHit);
    }
}
