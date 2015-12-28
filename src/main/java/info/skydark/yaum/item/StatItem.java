package info.skydark.yaum.item;

import cpw.mods.fml.relauncher.ReflectionHelper;
import info.skydark.yaum.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.TupleIntJsonSerializable;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

/**
 * Created by skydark on 15-8-26.
 */
public class StatItem extends ItemMod {
    public final static String name = "statItem";
    public final static String TAG_OWNER = "owner";
    public final static String TAG_STAT = "stat";

    public StatItem() {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        return itemStack.copy();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote && player instanceof EntityPlayerMP) {
            stack = dumpStat(stack, (EntityPlayerMP) player);
            player.addChatMessage(new ChatComponentText("Recorded!"));
        }
        return super.onItemRightClick(stack, world, player);
    }

    private ItemStack dumpStat(ItemStack stack, EntityPlayerMP player) {
        NBTHelper.setTag(stack, TAG_OWNER, player.getCommandSenderName());
        Map<StatBase, TupleIntJsonSerializable> statMap = ReflectionHelper.getPrivateValue(StatFileWriter.class, player.func_147099_x(), "field_150875_a");
        NBTHelper.setTag(stack, TAG_STAT, NBTHelper.fromStatMap(statMap));
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltips, boolean par4) {
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound == null || !nbtTagCompound.hasKey(TAG_OWNER)) {
            return;
        }
        String owner = nbtTagCompound.getString(TAG_OWNER);
        tooltips.add("owner: " + owner);
        if (owner.equals(player.getCommandSenderName())) {
            tooltips.add(EnumChatFormatting.GREEN + "you!");
        }
    }
}