package info.skydark.yaum.mt.drop;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.event.EventHandlerWrapper;
import info.skydark.yaum.mt.event.EventWrapper;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

/**
 * Created by skydark on 15-11-17.
 */
public class ModifierAdder implements EventHandlerWrapper.ILivingDropsEventHandler {
    private WeightedItemStack[] stack;
    private float total_weight;
    private int amount_min;
    private int amount_max;
    private float chance;

    public ModifierAdder(WeightedItemStack stack, int amount_min, int amount_max) {
        this(stack.getStack(), stack.getChance(), amount_min, amount_max);
    }

    public ModifierAdder(IItemStack stack, float chance, int amount_min, int amount_max) {
        this(new IItemStack[]{stack}, chance, amount_min, amount_max);
    }

    public ModifierAdder(IItemStack[] stack, float chance, int amount_min, int amount_max) {
        WeightedItemStack[] weightedItemStacks = new WeightedItemStack[stack.length];
        for (int i = 0; i < stack.length; i++) {
            weightedItemStacks[i] = new WeightedItemStack(stack[i], 1);
        }
        init(weightedItemStacks, chance, amount_min, amount_max);
    }

    public ModifierAdder(WeightedItemStack[] stack, float chance, int amount_min, int amount_max) {
        init(stack, chance, amount_min, amount_max);
    }

    private void init(WeightedItemStack[] stack, float chance, int amount_min, int amount_max) {
        this.stack = stack;
        total_weight = 0;
        for (WeightedItemStack s: stack) {
            total_weight += s.getChance();
        }
        this.chance = chance > 1.0f ? 1.0f : chance;
        if (amount_max == 0) {
            if (amount_min == 0) {
                this.amount_min = 0;
                this.amount_max = 1;
            } else {
                this.amount_min = 0;
                this.amount_max = amount_min;
            }
        } else {
            this.amount_min = amount_min;
            this.amount_max = amount_max >= amount_min ? amount_max : amount_min;
        }
    }

    protected int getAmount(Random rand, int amount_min, int amount_max, float chance, int lootingLevel, boolean recentlyHit) {
        int amount = amount_min;
        for (int i = amount_min; i < amount_max; i++) {
            if (rand.nextFloat() < chance) {
                amount++;
            }
        }
        return amount;
    }

    protected ItemStack pickStack(Random rand) {
        if (stack == null || stack.length == 0) return null;
        float weight = rand.nextFloat() * total_weight;
        WeightedItemStack choice = null;
        for (WeightedItemStack item : stack) {
            choice = item;
            weight -= item.getChance();
            if (weight <= 0) {
                break;
            }
        }
        ItemStack _stack = MTHelper.getItemStack(choice);
        return _stack == null ? null : _stack.copy();
    }

    protected int getTries(Random rand) {
        return 1;
    }

    @Override
    public void handle(EventWrapper.MyLivingDropsEvent event) {
        LivingDropsEvent ev = event.getInternal();
        EntityLivingBase entity = ev.entityLiving;
        if (entity != null) {
            Random rand = entity.getRNG();
            int loops = getTries(rand);
            for (int i = 0; i < loops; i++) {
                int amount = getAmount(rand, amount_min, amount_max, chance, ev.lootingLevel, ev.recentlyHit);
                if (amount == 0) {
                    continue;
                }
                ItemStack _stack = pickStack(rand);
                if (_stack == null) continue;
                _stack.stackSize = amount;
                ev.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, _stack));
            }
        }
    }
}
