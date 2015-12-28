package info.skydark.yaum.util;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by skydark on 15-12-11.
 */
public class EntityHelper {
    public static Entity spawnEntity(Entity entity,
                                      String name, NBTTagCompound nbttagcompound, boolean forceSpawnWithEgg) {
        return spawnEntity(entity.worldObj, entity.posX, entity.posY, entity.posZ, name, nbttagcompound, forceSpawnWithEgg);
    }

    public static Entity spawnEntity(World world, double x, double y, double z,
                                      String name, NBTTagCompound nbttagcompound, boolean forceSpawnWithEgg) {
        if (world.isRemote) {
            return null;
        }

        if (nbttagcompound == null) nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("id", name);
        Entity entity1 = EntityList.createEntityFromNBT(nbttagcompound, world);
        if (entity1 == null) {
            FMLLog.warning("spawn entity failed: " + name);
            return null;
        }
        entity1.setLocationAndAngles(x, y, z, entity1.rotationYaw, entity1.rotationPitch);
        if (forceSpawnWithEgg && entity1 instanceof EntityLiving) {
            ((EntityLiving) entity1).onSpawnWithEgg(null);
        }
        world.spawnEntityInWorld(entity1);
        Entity entity2 = entity1;
        for (NBTTagCompound nbttagcompound1 = nbttagcompound; entity2 != null && nbttagcompound1.hasKey("Riding", 10); nbttagcompound1 = nbttagcompound1.getCompoundTag("Riding"))
        {
            Entity entityx = EntityList.createEntityFromNBT(nbttagcompound1.getCompoundTag("Riding"), world);

            if (entityx != null)
            {
                entityx.setLocationAndAngles(x, y, z, entityx.rotationYaw, entityx.rotationPitch);
                world.spawnEntityInWorld(entityx);
                entity2.mountEntity(entityx);
            }

            entity2 = entityx;
        }
        return entity1;
    }
}
