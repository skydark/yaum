package info.skydark.yaum.item;

import cpw.mods.fml.common.registry.GameRegistry;
import info.skydark.yaum.Yaum;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by skydark on 15-11-13.
 */
public abstract class ItemMod extends Item {
    public ItemMod(String name) {
        super();
        setUnlocalizedName(name);
        GameRegistry.registerItem(this, name);
        setTextureName(Yaum.MODID + ":" + name);
        setCreativeTab(Yaum.yaumTab);
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        return super.getUnlocalizedNameInefficiently(stack).replaceAll("item\\.", "item." + Yaum.MODID + ":");
    }
}
