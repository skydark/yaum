package info.skydark.yaum.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.skydark.yaum.Yaum;
import info.skydark.yaum.tileentity.TileEntityOnceSpawner;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by skydark on 15-12-11.
 */
public class OnceSpawner extends BlockContainer {
    public final String name = "onceSpawner";

    public OnceSpawner() {
        super(Material.rock);
        setBlockName(Yaum.MODID + ":" + name);
        GameRegistry.registerBlock(this, Yaum.MODID + ":" + this.name);
        setCreativeTab(Yaum.yaumTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityOnceSpawner();
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Item.getItemById(0);
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        if(p_149689_6_.hasTagCompound()) {
            TileEntity onceSpawner = p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_);
            if (onceSpawner != null && (onceSpawner instanceof TileEntityOnceSpawner))
                ((TileEntityOnceSpawner) onceSpawner).readFromItemNBT(p_149689_6_.getTagCompound());
        }
    }
}
