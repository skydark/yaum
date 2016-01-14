package info.skydark.yaum.mt.var;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by skydark on 16-1-14.
 */
public class VarProperties implements IExtendedEntityProperties, IVarSet {
    public final static String ID = "yaum";
    private NBTTagCompound varMap = new NBTTagCompound();

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(ID, new VarProperties());
    }

    public static VarProperties getVarProperties(EntityPlayer player) {
        return (VarProperties) player.getExtendedProperties(ID);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setTag(ID, varMap);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        varMap = compound.getCompoundTag(ID);
    }

    @Override
    public void init(Entity entity, World world) {
    }

    @Override
    public void setVar(String name, int value) {
        varMap.setInteger(name, value);
    }

    @Override
    public int getVar(String name) {
        return varMap.getInteger(name);
    }
}
