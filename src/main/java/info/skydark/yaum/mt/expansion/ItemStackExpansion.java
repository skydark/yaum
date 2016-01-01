package info.skydark.yaum.mt.expansion;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.util.NBTHelper;
import minetweaker.MineTweakerAPI;
import minetweaker.api.data.IData;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-11-14.
 */
@ZenExpansion("minetweaker.item.IItemStack")
public class ItemStackExpansion {
    @ZenMethod
    public static IData getTagByPath(IItemStack stack, String path) {
        ItemStack _stack = MTHelper.getItemStack(stack);
        return _stack == null? null : MTHelper.getIData(NBTHelper.getTagByPath(_stack, path));
    }

    @ZenMethod
    public static boolean setTagByPath(IItemStack stack, String path, IData data) {
        ItemStack _stack = MTHelper.getItemStack(stack);
        return _stack != null && NBTHelper.setTagByPath(_stack, path, MTHelper.getNBT(data));
    }

    @ZenMethod
    public static void setHarvestLevel(IItemStack stack, String toolClass, int value) {
        ItemStack _stack = MTHelper.getItemStack(stack);
        if (_stack == null) return;
        MineTweakerAPI.apply(new SetHarvestLevelAction(_stack, toolClass, value));
    }
}
