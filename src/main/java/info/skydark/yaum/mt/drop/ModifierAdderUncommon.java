package info.skydark.yaum.mt.drop;

import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;

import java.util.Random;

/**
 * Created by skydark on 15-11-17.
 */
public class ModifierAdderUncommon extends ModifierAdder {
    public ModifierAdderUncommon(IItemStack stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    public ModifierAdderUncommon(WeightedItemStack stack, int amount_min, int amount_max) {
        super(stack, amount_min, amount_max);
    }

    public ModifierAdderUncommon(IItemStack[] stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    public ModifierAdderUncommon(WeightedItemStack[] stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    @Override
    protected int getAmount(Random rand, int amount_min, int amount_max, float chance, int lootingLevel, boolean recentlyHit) {
        if (!recentlyHit) return 0;
        int amount = super.getAmount(rand, amount_min, amount_max, chance, 0, recentlyHit);
        if (amount == 0 && lootingLevel > 0) {
            amount = super.getAmount(rand, amount_min, amount_max, 1.0f * lootingLevel / (lootingLevel + 1), 0, recentlyHit);
        }
        return amount;
    }
}
