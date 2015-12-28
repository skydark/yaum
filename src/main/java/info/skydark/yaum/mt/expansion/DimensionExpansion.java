package info.skydark.yaum.mt.expansion;

import cpw.mods.fml.relauncher.ReflectionHelper;
import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.var.IVarSet;
import info.skydark.yaum.mt.var.WorldVarSavedData;
import minetweaker.api.item.IItemStack;
import minetweaker.api.world.IDimension;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Map;

/**
 * Created by skydark on 15-11-15.
 */
@ZenExpansion("minetweaker.world.IDimension")
public class DimensionExpansion {
    @ZenGetter("id")
    public static int getId(IDimension iDimension) {
        return MTHelper.getDimensionId(iDimension);
    }

    @ZenGetter("varWorld")
    public static IVarSet getVarSetWorld(IDimension iDimension) {
        World world = MTHelper.getWorld(iDimension);
        return world == null? null : WorldVarSavedData.perWorld(world);
    }

    @ZenGetter("varMap")
    public static IVarSet getVarSetMap(IDimension iDimension) {
        World world = MTHelper.getWorld(iDimension);
        return world == null? null : WorldVarSavedData.perMap(world);
    }

    @ZenMethod
    public static String BGM(IDimension iDimension) {
        World world = MTHelper.getWorld(iDimension);
        if (world == null) return "";
        ISound bgm = getPlayingBGM(world);
        return bgm == null? "" : bgm.getPositionedSoundLocation().getResourcePath();
    }

    public static ISound getPlayingBGM(World world) {
        SoundManager sndManager = ReflectionHelper.getPrivateValue(SoundHandler.class, Minecraft.getMinecraft().getSoundHandler(), "sndManager", "field_147694_f");
        Map playingSounds = ReflectionHelper.getPrivateValue(SoundManager.class, sndManager, "playingSounds", "field_148629_h");
        for (Object o : playingSounds.keySet()) {
            PositionedSound ps = (PositionedSound) playingSounds.get(o);
            ResourceLocation reloc = ps.getPositionedSoundLocation();
            if ("minecraft".equals(reloc.getResourceDomain())) {
                String path = reloc.getResourcePath();
                if (path != null && ("music.game".equals(path) || path.startsWith("music.game."))) {
                    return ps;
                }
            }
        }
        return null;
    }

    @ZenMethod
    public static boolean isPlayingBGM(IDimension iDimension) {
        World world = MTHelper.getWorld(iDimension);
        if (world == null || !world.isRemote) {
            return false;
        }
        return getPlayingBGM(world) != null;
    }

    @ZenMethod
    public static void stopBGM(IDimension iDimension) {
        World world = MTHelper.getWorld(iDimension);
        if (world == null || !world.isRemote) {
            return;
        }
        ISound bgm = getPlayingBGM(world);
        if (bgm != null) {
            Minecraft.getMinecraft().getSoundHandler().stopSound(bgm);
        }
    }

    @ZenMethod
    public static void playBGM(IDimension iDimension, String name) {
        World world = MTHelper.getWorld(iDimension);
        if (world == null || !world.isRemote) {
            return;
        }
        stopBGM(iDimension);
        ISound sound = PositionedSoundRecord.func_147673_a(new ResourceLocation(name));
        Minecraft.getMinecraft().getSoundHandler().playSound(sound);
    }

    @ZenMethod
    public static void spawnItem(IDimension iDimension, IItemStack istack, int x, int y, int z) {
        World world = MTHelper.getWorld(iDimension);
        ItemStack stack = MTHelper.getItemStack(istack);
        if (world == null || world.isRemote || stack == null) {
            return;
        }
        world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y, z + 0.5, stack.copy()));
    }

    @ZenMethod
    public static void setBlock(IDimension iDimension, IItemStack istack, int x, int y, int z) {
        World world = MTHelper.getWorld(iDimension);
        ItemStack stack = MTHelper.getItemStack(istack);
        if (world == null || world.isRemote || stack == null) {
            return;
        }
        world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 3);
    }

    @ZenMethod
    public static void setBlockToAir(IDimension iDimension, int x, int y, int z) {
        World world = MTHelper.getWorld(iDimension);
        if (world == null || world.isRemote) {
            return;
        }
        world.setBlockToAir(x, y, z);
    }

    @ZenMethod
    public static boolean matchBlock(IDimension iDimension, int x, int y, int z, String name) {
        World world = MTHelper.getWorld(iDimension);
        return world != null && MTHelper.matchBlock(name, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
    }
}
