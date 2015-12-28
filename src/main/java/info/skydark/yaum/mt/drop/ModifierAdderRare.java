package info.skydark.yaum.mt.drop;

import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;

import java.util.Random;

/**
 * Created by skydark on 15-11-17.
 */
public class ModifierAdderRare extends ModifierAdder {
    public ModifierAdderRare(IItemStack stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    public ModifierAdderRare(WeightedItemStack stack, int amount_min, int amount_max) {
        super(stack, amount_min, amount_max);
    }

    public ModifierAdderRare(IItemStack[] stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    public ModifierAdderRare(WeightedItemStack[] stack, float chance, int amount_min, int amount_max) {
        super(stack, chance, amount_min, amount_max);
    }

    @Override
    protected int getAmount(Random rand, int amount_min, int amount_max, float chance, int lootingLevel, boolean recentlyHit) {
        if (!recentlyHit) return 0;
        chance += 0.01f * lootingLevel;
        chance = chance > 1.0f ? 1.0f : chance;
        return super.getAmount(rand, amount_min, amount_max, chance, 0, recentlyHit);
    }
}
