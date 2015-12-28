package info.skydark.yaum.dispenser;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by skydark on 15-11-23.
 */
public class BehaviorWaterBucket extends BehaviorDefaultDispenseItem {
    private final IBehaviorDispenseItem vanillaBehavior;
    public BehaviorWaterBucket() {
        vanillaBehavior = (IBehaviorDispenseItem) BlockDispenser.dispenseBehaviorRegistry.getObject(Items.water_bucket);
    }

    public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
        ForgeDirection facing = ForgeDirection.getOrientation(BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata()).ordinal());
        int x = p_82487_1_.getXInt() + facing.offsetX;
        int y = p_82487_1_.getYInt() + facing.offsetY;
        int z = p_82487_1_.getZInt() + facing.offsetZ;
        World world = p_82487_1_.getWorld();
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (block instanceof BlockCauldron && meta < 3) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 3);
            p_82487_2_.func_150996_a(Items.bucket);
            p_82487_2_.stackSize = 1;
            return p_82487_2_;
        } else {
            return vanillaBehavior.dispense(p_82487_1_, p_82487_2_);
        }
    }
}
