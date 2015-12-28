package info.skydark.yaum.mt.var;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

/**
 * Created by skydark on 15-12-17.
 */
public class WorldVarSavedData extends WorldSavedData implements IVarSet {
    public final static String ID = "yaum";
    private NBTTagCompound varMap;
    private final String identifier;

    public WorldVarSavedData(String id) {
        super(id);
        identifier = id;
        varMap = new NBTTagCompound();
    }

    private static WorldVarSavedData perMapStorage(String id, MapStorage mapStorage, World world) {
        WorldVarSavedData data = (WorldVarSavedData) mapStorage.loadData(WorldVarSavedData.class, id);
        if (data == null) {
            data = new WorldVarSavedData(id);
            mapStorage.setData(id, data);
        }
        return data;
    }

    public static WorldVarSavedData perMapPlayer(EntityPlayer player) {
        String uuid = player.getUniqueID().toString();
        return perMapStorage(ID + "-" + uuid, player.worldObj.mapStorage, player.worldObj);
    }

    public static WorldVarSavedData perWorldPlayer(World world, EntityPlayer player) {
        String uuid = player.getUniqueID().toString();
        return perMapStorage(ID + "-" + uuid, world.perWorldStorage, world);
    }


    public static WorldVarSavedData perMap(World world) {
        return perMapStorage(ID, world.mapStorage, world);
    }

    public static WorldVarSavedData perWorld(World world) {
        return perMapStorage(ID, world.perWorldStorage, world);
    }

    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {
        varMap = p_76184_1_.getCompoundTag(ID);
    }

    @Override
    public void writeToNBT(NBTTagCompound p_76187_1_) {
        p_76187_1_.setTag(ID, varMap);
    }

    @Override
    public void setVar(String name, int value) {
        varMap.setInteger(name, value);
        this.markDirty();
    }

    @Override
    public int getVar(String name) {
        return varMap.getInteger(name);
    }
}
