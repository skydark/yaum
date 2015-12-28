package info.skydark.yaum.mt.expansion;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.type.IEntityLivingBase;
import info.skydark.yaum.mt.type.IPlayerPlus;
import info.skydark.yaum.util.EntityHelper;
import minetweaker.MineTweakerAPI;
import minetweaker.api.data.IData;
import minetweaker.api.entity.IEntity;
import minetweaker.api.item.IItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-11-15.
 */
@ZenExpansion("minetweaker.entity.IEntity")
public class EntityExpansion {
    @ZenGetter("id")
    public static String getId(IEntity iEntity) {
        return null;  // same as MCPlayer.getId
    }

    @ZenGetter("name")
    public static String getName(IEntity iEntity) {
        Entity entity = MTHelper.getEntity(iEntity);
        return entity == null ? null : EntityList.getEntityString(entity);
    }

    @ZenGetter("displayName")
    public static String getDisplayName(IEntity iEntity) {
        Entity entity = MTHelper.getEntity(iEntity);
        return entity == null ? null : entity.getCommandSenderName();
    }

    @ZenGetter("data")
    public static IData getData(IEntity iEntity) {
        Entity entity = MTHelper.getEntity(iEntity);
        return entity == null ? null : MTHelper.getIData(entity.getEntityData());
    }

    @ZenMethod
    public static IEntityLivingBase asLivingBase(IEntity iEntity) {
        Entity entity = MTHelper.getEntity(iEntity);
        return entity instanceof EntityLivingBase ? MTHelper.getIEntityLivingBase((EntityLivingBase) entity) : null;
    }

    @ZenMethod
    public static IPlayerPlus asPlayer(IEntity iEntity) {
        Entity entity = MTHelper.getEntity(iEntity);
        return entity instanceof EntityPlayer ? MTHelper.getIPlayer((EntityPlayer) entity) : null;
    }

    @ZenMethod
    public static boolean match(IEntity iEntity, String name) {
        return MTHelper.matchEntity(name, iEntity);
    }

    @ZenMethod
    public static boolean kill(IEntity iEntity) {
        Entity entity = MTHelper.getEntity(iEntity);
        if (entity == null || !entity.isEntityAlive())
            return false;
        return entity.attackEntityFrom(DamageSource.outOfWorld,Float.MAX_VALUE);
    }

    @ZenMethod
    public static void spawnItem(IEntity iEntity, IItemStack istack) {
        Entity entity = MTHelper.getEntity(iEntity);
        ItemStack stack = MTHelper.getItemStack(istack);
        if (entity == null || stack == null || entity.worldObj.isRemote) return;
        entity.worldObj.spawnEntityInWorld(
                new EntityItem(entity.worldObj, entity.posX + 0.5, entity.posY, entity.posZ + 0.5, stack.copy()));
    }

    @ZenMethod
    public static IEntity spawnEntity(IEntity iEntity, String name, @Optional IData nbtdata) {
        Entity entity = MTHelper.getEntity(iEntity);
        if (entity == null || name == null) return null;
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        if (nbtdata != null) {
            try {
                NBTBase nbt = MTHelper.getNBT(nbtdata);
                if (!(nbt instanceof NBTTagCompound)) {
                    MineTweakerAPI.logError("not a valid nbt");
                    return null;
                }
                nbttagcompound = (NBTTagCompound) nbt;
            } catch (Throwable e) {
                MineTweakerAPI.logError("bad nbt data");
                return null;
            }
        }
        return MTHelper.getIEntity(EntityHelper.spawnEntity(entity, name, nbttagcompound,
                nbtdata == null || nbttagcompound.getBoolean("forceSpawnWithEgg")));
    }
}
