package info.skydark.yaum.mt.drop;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.event.EventHandlerWrapper;
import info.skydark.yaum.mt.event.EventWrapper;
import minetweaker.api.item.IIngredient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by skydark on 15-11-17.
 */
public class ModifierRemover implements EventHandlerWrapper.ILivingDropsEventHandler {
    private IIngredient ingredient;
    private int amount;

    public ModifierRemover(IIngredient ingredient, int amount, boolean removeAll) {
        this.ingredient = ingredient;
        if (removeAll) {
            this.amount = 65536;
        } else {
            this.amount = amount;
        }
        // this.ingredient.amount(1);
    }

    public ModifierRemover(IIngredient ingredient) {
        this(ingredient, 0, true);
    }

    @Override
    public void handle(EventWrapper.MyLivingDropsEvent event) {
        int count = amount;
        LivingDropsEvent ev = event.getInternal();
        EntityLivingBase entity = ev.entityLiving;
        if (entity == null) return;
        ArrayList<EntityItem> drops = ev.drops;
        for (Iterator<EntityItem> iterator = drops.iterator(); iterator.hasNext(); ) {
            EntityItem entityItem = iterator.next();
            ItemStack stack = entityItem.getEntityItem();
            if (MTHelper.matchItemStack(ingredient, stack)) {
                if (stack.stackSize > count) {
                    stack.stackSize -= count;
                    entityItem.setEntityItemStack(stack);
                    return;
                } else if (stack.stackSize == count) {
                    iterator.remove();
                    return;
                }
                count -= stack.stackSize;
                iterator.remove();
            }
        }
    }
}
