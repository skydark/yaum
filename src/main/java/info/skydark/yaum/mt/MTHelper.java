package info.skydark.yaum.mt;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import info.skydark.yaum.mt.expansion.EntityExpansion;
import info.skydark.yaum.mt.type.IEntityLivingBase;
import info.skydark.yaum.mt.type.IMyDamageSource;
import info.skydark.yaum.mt.type.IPlayerPlus;
import info.skydark.yaum.mt.value.*;
import minetweaker.MineTweakerAPI;
import minetweaker.api.data.IData;
import minetweaker.api.entity.IEntity;
import minetweaker.api.entity.IEntityItem;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.api.world.IDimension;
import minetweaker.mc1710.brackets.OreBracketHandler;
import minetweaker.mc1710.world.MCDimension;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by skydark on 15-11-18.
 */
public class MTHelper {
    public static boolean matchItemStack(IIngredient ingredient, ItemStack stack) {
        if (ingredient == null || stack == null || stack.getItem() == null) return false;
        return ingredient.matches(MTHelper.getIItemStack(stack));
    }

    public static IData getIData(NBTBase nbt) {
        return MineTweakerMC.getIData(nbt);
    }

    public static NBTBase getNBT(IData data) {
        return MineTweakerMC.getNBT(data);
    }

    public static ItemStack getItemStack(WeightedItemStack stack) {
        if (stack == null) return null;
        return MineTweakerMC.getItemStack(stack.getStack());
    }

    public static ItemStack getItemStack(IItemStack stack) {
        return MineTweakerMC.getItemStack(stack);
    }

    public static IItemStack getIItemStack(ItemStack stack) {
        return MineTweakerMC.getIItemStack(stack);
    }

    public static IPlayerPlus getIPlayer(EntityPlayer player) {
        return player == null ? null : new MCPlayerPlus(player);
    }

    public static EntityPlayer getPlayer(IPlayer player) {
        return MineTweakerMC.getPlayer(player);
    }

    public static Entity getEntity(IEntity entity) {
        if (entity == null) return null;
        if (entity instanceof MCEntity) {
            return ((MCEntity) entity).getInternal();
        } else if (entity instanceof MCPlayerPlus) {
            return ((MCPlayerPlus) entity).getInternal();
        } else {
            MineTweakerAPI.logError("Invalid entity: " + entity);
        }
        return null;
    }

    public static IEntity getIEntity(Entity entity) {
        return entity == null ? null : new MCEntity(entity);
    }

    public static IEntityLivingBase getIEntityLivingBase(EntityLivingBase entity) {
        return entity == null ? null : new MCEntityLivingBase(entity);
    }

    public static EntityLivingBase getEntityLivingBase(IEntityLivingBase entity) {
        if (entity == null) return null;
        if (!(entity instanceof MCEntityLivingBase)) {
            MineTweakerAPI.logError("Invalid entity living base: " + entity);
        }
        return ((MCEntityLivingBase) entity).getInternal();
    }

    public static IDimension getIDimension(int dim) {
        World world = DimensionManager.getWorld(dim);
        return new MCDimension(world);
    }

    public static IDimension getIDimension(World world) {
        return world == null ? null : new MCDimension(world);
    }

    public static World getWorld(IDimension iDimension) {
        if (iDimension instanceof MCDimension) {
            return ReflectionHelper.getPrivateValue(MCDimension.class, (MCDimension) iDimension, "world");
        }
        MineTweakerAPI.logError("Invalid dimension: " + iDimension);
        return null;
    }

    public static int getDimensionId(IDimension iDimension) {
        World world = getWorld(iDimension);
        return world == null? 0 : world.provider.dimensionId;
    }

    public static IMyDamageSource getIMyDamageSource(DamageSource source) {
        return source == null ? null : new MCDamageSource(source);
    }

    public static IEntityItem getIEntityItem(EntityItem entityItem) {
        return entityItem == null ? null : new MCEntityItem(entityItem);
    }

    public static boolean matchEntity(String pattern, IEntity iEntity) {
        if (pattern == null) return false;
        Entity entity = MTHelper.getEntity(iEntity);
        if (entity == null) return false;
        String entityName = EntityExpansion.getName(iEntity);
        // String entityClassName = entity.getClass().getSimpleName();
        if (pattern.equals("*")) return true;
        for (String pattern1: pattern.split("\\|")) {
            if (pattern1.isEmpty()) {
                MineTweakerAPI.logError("empty pattern is not allowed while matching");
                return false;
            }
            boolean flag = true;
            for (String pattern2: pattern1.split("&")) {
                boolean reversed = pattern2.startsWith("!");
                if (reversed) {
                    pattern2 = pattern2.substring(1);
                }
                if (pattern2.startsWith("<") && pattern2.endsWith(">")) {
                    if (pattern2.equals("<living>")) {
                        if (reversed != (entity instanceof EntityLiving)) continue;
                    } else if (pattern2.equals("<livingbase>")) {
                        if (reversed != (entity instanceof EntityLivingBase)) continue;
                    } else if (pattern2.equals("<player>")) {
                        if (reversed != (entity instanceof EntityPlayer)) continue;
                    } else if (pattern2.equals("<tameable>")) {
                        if (reversed != (entity instanceof EntityTameable)) continue;
                    } else if (pattern2.equals("<animal>")) {
                        if (reversed != (entity instanceof EntityAnimal)) continue;
                    } else if (pattern2.equals("<creature>")) {
                        if (reversed != (entity instanceof EntityCreature)) continue;
                    } else if (pattern2.equals("<mob>")) {
                        if (reversed != (entity instanceof EntityMob)) continue;
                    } else if (pattern2.equals("<monster>")) {
                        if (reversed != (entity instanceof IMob)) continue;
                    } else if (pattern2.equals("<boss>")) {
                        if (reversed != (entity instanceof IBossDisplayData)) continue;
                    } else if (pattern2.equals("<undead>")) {
                        if (reversed !=
                                ((entity instanceof EntityLivingBase)
                                        && ((EntityLivingBase) entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)) {
                            continue;
                        }
                    } else if (pattern2.equals("<arthropod>")) {
                        if (reversed !=
                                ((entity instanceof EntityLivingBase)
                                        && ((EntityLivingBase) entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD)) {
                            continue;
                        }
                    } else if (pattern2.equals("<witherskeleton>")) {
                        if (reversed !=
                                ((entity instanceof EntitySkeleton)
                                        && ((EntitySkeleton) entity).getSkeletonType() == 1)) {
                            continue;
                        }
                    } else {
                        MineTweakerAPI.logError("Unknown entity pattern:" + pattern);
                    }
                } else if (pattern2.startsWith("#")) {
                    if (entity instanceof EntityLiving) {
                        String customName = ((EntityLiving) entity).getCustomNameTag();
                        if (reversed != pattern2.substring(1).equals(customName)) {
                            continue;
                        }
                    }
                } else if (reversed != pattern2.equals(entityName)) {
                    continue;
                }
                flag = false;
                break;
            }
            if (flag) return true;
        }
        return false;
    }

    public static boolean matchBlock(String pattern, final Block block, int meta) {
        if (pattern.equals("*")) return true;
        String string = GameRegistry.findUniqueIdentifierFor(block).toString();
        String stringstar = string + ":*";
        String stringmeta = string + ":" + Integer.toString(meta);
        for (String pattern1: pattern.split("\\|")) {
            if (pattern1.isEmpty()) {
                MineTweakerAPI.logError("empty pattern is not allowed while matching");
                return false;
            }
            boolean flag = true;
            for (String pattern2: pattern1.split("&")) {
                boolean reversed = pattern2.startsWith("!");
                if (reversed) {
                    pattern2 = pattern2.substring(1);
                }
                if (pattern2.startsWith("<") && pattern2.endsWith(">")) {
                    Material material = block.getMaterial();
                    if (pattern.startsWith("<material:")) {
                        if (pattern.equals("<material:liquid>")) {
                            if (reversed != material.isLiquid()) continue;
                        } else if (pattern.equals("<material:opaque>")) {
                            if (reversed != material.isOpaque()) continue;
                        } else if (pattern.equals("<material:solid>")) {
                            if (reversed != material.isSolid()) continue;
                        } else if (pattern.equals("<material:air>")) {
                            if (reversed != (material == Material.air)) continue;
                        } else if (pattern.equals("<material:wood>")) {
                            if (reversed != (material == Material.wood)) continue;
                        } else if (pattern.equals("<material:rock>")) {
                            if (reversed != (material == Material.rock)) continue;
                        } else if (pattern.equals("<material:iron>")) {
                            if (reversed != (material == Material.iron)) continue;
                        } else if (pattern.equals("<material:glass>")) {
                            if (reversed != (material == Material.glass)) continue;
                        } else if (pattern.equals("<material:ice>")) {
                            if (reversed != (material == Material.ice)) continue;
                        } else if (pattern.equals("<material:water>")) {
                            if (reversed != (material == Material.water)) continue;
                        } else if (pattern.equals("<material:sand>")) {
                            if (reversed != (material == Material.sand)) continue;
                        } else if (pattern.equals("<material:snow>")) {
                            if (reversed != (material == Material.snow)) continue;
                        } else {
                            MineTweakerAPI.logError("Unknown material pattern:" + pattern);
                        }
                    } else if (pattern.startsWith("<ore:")) {
                        String orename = pattern.substring("<ore:".length(), pattern.length()-1);
                        if (reversed != matchItemStack(OreBracketHandler.getOre(orename), new ItemStack(block, 1, meta))) {
                            continue;
                        }
                    } else {
                        MineTweakerAPI.logError("Unknown block pattern:" + pattern);
                    }
                    flag = false;
                    break;
                } else {
                    if (reversed) {
                        if ((meta != 0 || !pattern2.equals(string)) &&
                                !pattern2.equals(stringstar) && !pattern2.equals(stringmeta)) continue;
                    } else {
                        if (meta == 0 && pattern2.equals(string)) continue;
                        if (pattern2.equals(stringstar)) continue;
                        if (pattern2.equals(stringmeta)) continue;
                    }
                    flag = false;
                    break;
                }
            }
            if (flag) return true;
        }
        return false;
    }
}
