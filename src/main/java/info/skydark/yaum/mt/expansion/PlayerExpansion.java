package info.skydark.yaum.mt.expansion;

import baubles.api.BaublesApi;
import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.type.IEntityLivingBase;
import info.skydark.yaum.mt.type.IPlayerPlus;
import info.skydark.yaum.mt.var.IVarSet;
import info.skydark.yaum.mt.var.VarProperties;
import info.skydark.yaum.mt.var.WorldVarSavedData;
import minetweaker.api.entity.IEntity;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;
import stanhebben.zenscript.annotations.*;

import java.util.Random;


/**
 * Created by skydark on 15-11-15.
 */
@ZenExpansion("minetweaker.player.IPlayer")
public class PlayerExpansion {
    @ZenMethod
    public static void givePlus(IPlayer iPlayer, IItemStack istack) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player == null || istack == null) return;
        ItemStack stack = MTHelper.getItemStack(istack);
        stack = stack.copy();
        if (!player.inventory.addItemStackToInventory(stack)) {
            if (!player.worldObj.isRemote) {
                player.worldObj.spawnEntityInWorld(
                        new EntityItem(player.worldObj, player.posX + 0.5, player.posY, player.posZ + 0.5, stack));
            }
        }
    }

    @ZenGetter
    public static boolean isRemote(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player != null && player.worldObj.isRemote;
    }

    @ZenGetter
    public static boolean isSneaking(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player != null && player.isSneaking();
    }

    @ZenMethod
    public static void sendChatLocal(IPlayer iPlayer, String message, @Optional boolean clientSide) {
        if (clientSide != isRemote(iPlayer)) return;
        iPlayer.sendChat(StatCollector.translateToLocal(message));
    }

    @ZenGetter("flag")
    public static IVarSet getFlag(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player == null? null : VarProperties.getVarProperties(player);
    }

    @ZenGetter("varWorld")
    public static IVarSet getVarSetWorld(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player == null? null : WorldVarSavedData.perWorldPlayer(player.worldObj, player);
    }

    @ZenGetter("varMap")
    public static IVarSet getVarSetMap(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player == null? null : WorldVarSavedData.perMapPlayer(player);
    }

    /*
      return true if it is the first time player gets the achievement
     */
    @ZenMethod
    public static boolean addStat(IPlayer iPlayer, String name, int value) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        StatBase stat = StatList.func_151177_a(name);
        if (player == null || stat == null) return false;
        if (!(player instanceof EntityPlayerMP)) return false;
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        int pre = stat.isAchievement() ? playerMP.func_147099_x().writeStat(stat) : 0;
        player.addStat(stat, value);
        int after = stat.isAchievement() ? playerMP.func_147099_x().writeStat(stat) : 0;
        return pre == 0 && after > pre;
    }

    @ZenMethod
    public static int getStat(IPlayer iPlayer, String name) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        StatBase stat = StatList.func_151177_a(name);
        if (player == null || stat == null) return 0;
        if (!(player instanceof EntityPlayerMP)) return 0;
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        return playerMP.func_147099_x().writeStat(stat);
    }

    @ZenMethod
    public static IEntityLivingBase asLivingBase(IPlayer iPlayer) {
        return MTHelper.getIEntityLivingBase(MTHelper.getPlayer(iPlayer));
    }

    @ZenMethod
    public static IPlayerPlus asPlayer(IPlayer iPlayer) {
        return MTHelper.getIPlayer(MTHelper.getPlayer(iPlayer));
    }

    @ZenGetter
    public static boolean isBlocking(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player != null && player.isBlocking();
    }

    @ZenMethod("dropCurrentItem")
    public static void dropOneItem(IPlayer iPlayer, @Optional boolean all) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player != null) {
            player.dropOneItem(all);
        }
    }

    @ZenGetter("level")
    public static int getLevel(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player == null ? 0 : player.experienceLevel;
    }

    @ZenSetter("level")
    public static void setLevel(IPlayer iPlayer, int level) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player == null || level == player.experienceLevel) return;
        if (level > player.experienceLevel) {
            player.experienceTotal += player.xpBarCap() * (1.0f - player.experience);
            player.experienceLevel++;
        } else {
            player.experienceTotal = 0;
            player.experienceLevel = 0;
        }
        while (player.experienceLevel < level) {
            player.experienceTotal += player.xpBarCap();
            player.experienceLevel++;
        }
        player.experienceTotal += player.xpBarCap() * player.experience;
    }

    @ZenMethod
    public static void addLevel(IPlayer iPlayer, int level) {
        setLevel(iPlayer, getLevel(iPlayer) + level);
    }

    @ZenGetter("xp")
    public static int getXp(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player == null ? 0 : player.experienceTotal;
    }

    @ZenSetter("xp")
    public static void setXp(IPlayer iPlayer, int xp) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player == null) return;
        if (xp >= player.experienceTotal) {
            player.addExperience(xp - player.experienceTotal);
            return;
        }
        player.experienceTotal = 0;
        player.experience = 0.0f;
        player.experienceLevel = 0;
        if (xp > 0) {
            player.experience += (float) xp / (float) player.xpBarCap();
            for (player.experienceTotal = xp; player.experience >= 1.0F; player.experience /= (float) player.xpBarCap())
            {
                player.experience = (player.experience - 1.0F) * (float) player.xpBarCap();
                player.experienceLevel++;
            }
        }
    }

    @ZenMethod
    public static void addXp(IPlayer iPlayer, int xp) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player == null) return;
        if (xp >= 0) {
            player.addExperience(xp);
        } else if (player.experience * player.xpBarCap() + xp >= 0) {
            player.experienceTotal += xp;
            player.experience += (float) xp / (float) player.xpBarCap();
        } else {
            setXp(iPlayer, player.experienceTotal + xp);
        }
    }

    @ZenGetter("foodLevel")
    public static int getFoodLevel(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player == null ? 0 : player.getFoodStats().getFoodLevel();
    }

    @ZenGetter("saturationLevel")
    public static float getSaturationLevel(IPlayer iPlayer) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        return player == null ? 0 : player.getFoodStats().getSaturationLevel();
    }

    @ZenMethod
    public static void eatFood(IPlayer iPlayer, int foodlv, float saturationlv) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player != null) {
            player.getFoodStats().addStats(foodlv, saturationlv);
        }
    }

    @ZenMethod
    public static int findItem(IPlayer iPlayer, IIngredient ingredient) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player == null) return -1;
        ItemStack[] mainInventory = player.inventory.mainInventory;
        for (int i = 0; i < mainInventory.length; i++) {
            ItemStack stack = mainInventory[i];
            if (MTHelper.matchItemStack(ingredient, stack)) {
                return i;
            }
        }
        return -1;
    }

    @ZenMethod
    public static boolean consumeItem(IPlayer iPlayer, IIngredient ingredient) {
        int pos = findItem(iPlayer, ingredient);
        if (pos < 0) return false;
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        ItemStack[] mainInventory = player.inventory.mainInventory;
        if (--mainInventory[pos].stackSize <= 0) {
            mainInventory[pos] = null;
        }
        return true;
    }

    @ZenMethod
    public static boolean tame(IPlayer iPlayer, IEntity iEntity, @Optional boolean hasParticle, @Optional boolean force) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player == null) return false;
        Entity entity = MTHelper.getEntity(iEntity);
        if (entity == null || !(entity instanceof EntityLivingBase)) return false;
        if (!force && entity instanceof EntityTameable && ((EntityTameable) entity).isTamed()) {
            return false;
        }
        if (entity instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) entity;
            if (!force && horse.isTame()) {
                return false;
            }
            horse.setTamedBy(player);
        } else if (entity instanceof EntityOcelot) {
            EntityOcelot ocelot = (EntityOcelot) entity;
            ocelot.setTamed(true);
            ocelot.setTameSkin(1 + ocelot.worldObj.rand.nextInt(3));
            ocelot.func_152115_b(player.getUniqueID().toString());
            for (Object entry : ocelot.tasks.taskEntries.toArray()) {
                EntityAIBase ai = ((EntityAITasks.EntityAITaskEntry) entry).action;
                if (ai instanceof EntityAISit) {
                    ((EntityAISit) ai).setSitting(true);
                    break;
                }
            }
            ocelot.worldObj.setEntityState(ocelot, (byte)7);
        } else if (entity instanceof EntityWolf) {
            EntityWolf wolf = (EntityWolf) entity;
            wolf.setTamed(true);
            wolf.setPathToEntity(null);
            wolf.setAttackTarget(null);
            for (Object entry : wolf.tasks.taskEntries.toArray()) {
                EntityAIBase ai = ((EntityAITasks.EntityAITaskEntry) entry).action;
                if (ai instanceof EntityAISit) {
                    ((EntityAISit) ai).setSitting(true);
                    break;
                }
            }
            wolf.setHealth(20.0F);
            wolf.func_152115_b(player.getUniqueID().toString());
            wolf.worldObj.setEntityState(wolf, (byte)7);
        } else {
            return false;
        }
        if (hasParticle) {
            Random rand = ((EntityLivingBase) entity).getRNG();
            for (int i = 0; i < 7; ++i) {
                double d0 = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                entity.worldObj.spawnParticle("heart", entity.posX + (double) (rand.nextFloat() * entity.width * 2.0F) - (double) entity.width, entity.posY + 0.5D + (double) (rand.nextFloat() * entity.height), entity.posZ + (double) (rand.nextFloat() * entity.width * 2.0F) - (double) entity.width, d0, d1, d2);
            }
        }
        return true;
    }

    @ZenMethod
    public static boolean hasBauble(IPlayer iPlayer, IIngredient ingredient) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player == null) return false;
        IInventory inventory = BaublesApi.getBaubles(player);
        if (inventory == null) return false;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (MTHelper.matchItemStack(ingredient, stack)) {
                return true;
            }
        }
        return false;
    }

    @ZenMethod
    public static boolean hasArmor(IPlayer iPlayer, IIngredient ingredient, @Optional int pos) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player == null) return false;
        ItemStack[] inventory = player.inventory.armorInventory;
        if (pos == 0) {
            for (int i = 0; i < inventory.length; i++) {
                ItemStack stack = inventory[i];
                if (MTHelper.matchItemStack(ingredient, stack)) {
                    return true;
                }
            }
            return false;
        }
        pos--;
        if (pos < 0 || pos >= inventory.length) return false;
        ItemStack stack = inventory[pos];
        return MTHelper.matchItemStack(ingredient, stack);
    }
}