package info.skydark.yaum.mt.drop;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.event.EventHandlerWrapper;
import info.skydark.yaum.mt.event.EventWrapper;
import info.skydark.yaum.mt.event.MTEvents;
import info.skydark.yaum.mt.type.ILivingDropsMatcher;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


/**
 * Created by skydark on 15-11-16.
 */
@ZenClass("mod.yaum.Drops")
public class Drops {
    public static final float CHANCE_RARE = 0.025f;
    public static final float CHANCE_UNCOMMON = 0.333f;

    public static void onDrops(final ILivingDropsMatcher matcher, final EventHandlerWrapper.ILivingDropsEventHandler modifier) {
        MTEvents.onLivingDrop(new EventHandlerWrapper.ILivingDropsEventHandler() {
            @Override
            public void handle(EventWrapper.MyLivingDropsEvent event) {
                if (matcher.match(event)) {
                    modifier.handle(event);
                }
            }
        });
    }

    @ZenMethod("add")
    public static void addDropsWeighted(String name, WeightedItemStack stack, @Optional int minAmount, @Optional int maxAmount, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdder(stack, minAmount, maxAmount));
    }

    @ZenMethod("add")
    public static void addDrops(String name, IItemStack stack, @Optional int minAmount, @Optional int maxAmount, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdder(stack, 0.5f, minAmount, maxAmount));
    }

    @ZenMethod("addSingle")
    public static void addDropsChance(String name, IItemStack stack, double chance, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdder(stack, (float) chance, 0, 1));
    }

    @ZenMethod("addCommon")
    public static void addCommonDropsWeighted(String name, WeightedItemStack stack, @Optional int minAmount, @Optional int maxAmount, @Optional ILivingDropsMatcher function) {
        if (minAmount == 0 && maxAmount == 0) {
            minAmount = 0;
            maxAmount = 3;
        }
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdderCommon(stack, minAmount, maxAmount));
    }

    @ZenMethod("addCommon")
    public static void addCommonDrops(String name, IItemStack stack, @Optional int minAmount, @Optional int maxAmount, @Optional ILivingDropsMatcher function) {
        if (minAmount == 0 && maxAmount == 0) {
            minAmount = 0;
            maxAmount = 3;
        }
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdderCommon(stack, 0.5f, minAmount, maxAmount));
    }

    @ZenMethod("addUncommon")
    public static void addUncommonDropsWeighted(String name, WeightedItemStack stack, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdderUncommon(stack, 0, 1));
    }

    @ZenMethod("addUncommon")
    public static void addUncommonDrops(String name, IItemStack stack, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdderUncommon(stack, CHANCE_UNCOMMON, 0, 1));
    }


    @ZenMethod("addRare")
    public static void addRareDropsWeighted(String name, WeightedItemStack stack, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdderRare(stack, 0, 1));
    }

    @ZenMethod("addRare")
    public static void addRareDrops(String name, IItemStack stack, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdderRare(stack, CHANCE_RARE, 0, 1));
    }

    @ZenMethod
    public static void addDroplist(String name, IItemStack[] stack, int minTries, int maxTries, int minAmount, int maxAmount, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdderDroplist(stack, minTries, maxTries, minAmount, maxAmount));
    }

    @ZenMethod
    public static void addDroplistWeighted(String name, WeightedItemStack[] stack, int minTries, int maxTries, int minAmount, int maxAmount, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierAdderDroplist(stack, minTries, maxTries, minAmount, maxAmount));
    }

    @ZenMethod("removeCount")
    public static void removeDropsCount(String name, IIngredient ingredient, @Optional int amount, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function),
                new ModifierRemover(ingredient, amount == 0 ? ingredient.getAmount() : amount, false));
    }

    @ZenMethod("remove")
    public static void removeDrops(String name, IIngredient ingredient, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function), new ModifierRemover(ingredient));
    }

    @ZenMethod("remove")
    public static void removeAllDrops(String name, @Optional ILivingDropsMatcher function) {
        onDrops(new DropsMatcherByNameAndFunction(name, function), new EventHandlerWrapper.ILivingDropsEventHandler() {
            @Override
            public void handle(EventWrapper.MyLivingDropsEvent event) {
                event.getInternal().drops.clear();
            }
        });
    }

    public static class DropsMatcherByNameAndFunction implements ILivingDropsMatcher {
        private String name;
        private ILivingDropsMatcher function;

        public DropsMatcherByNameAndFunction(String name, ILivingDropsMatcher function) {
            this.name = name;
            this.function = function;
        }

        @Override
        public boolean match(EventWrapper.MyLivingDropsEvent ev) {
            return MTHelper.matchEntity(name, ev.getEntityLiving()) && (function == null || function.match(ev));
        }
    }
}
