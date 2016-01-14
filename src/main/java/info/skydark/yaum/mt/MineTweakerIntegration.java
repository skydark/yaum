package info.skydark.yaum.mt;

import cpw.mods.fml.common.FMLCommonHandler;
import info.skydark.yaum.mt.drop.Drops;
import info.skydark.yaum.mt.event.*;
import info.skydark.yaum.mt.expansion.*;
import info.skydark.yaum.mt.type.*;
import info.skydark.yaum.mt.var.IVarSet;
import info.skydark.yaum.mt.var.VarHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.util.IEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import stanhebben.zenscript.annotations.ZenClass;

import java.lang.annotation.Annotation;

/**
 * Created by skydark on 15-11-14.
 */
public class MineTweakerIntegration {
    public static void init() {
        MineTweakerAPI.registerClass(Cast.class);
        MineTweakerAPI.registerClass(Rand.class);
        MineTweakerAPI.registerClass(Command.class);

        MineTweakerAPI.registerClass(Drops.class);
        MineTweakerAPI.registerClass(Interact.class);

        MineTweakerAPI.registerClass(IEntityLivingBase.class);
        MineTweakerAPI.registerClass(IMyDamageSource.class);
        MineTweakerAPI.registerClass(IPlayerPlus.class);
        MineTweakerAPI.registerClass(ILivingDropsMatcher.class);

        MineTweakerAPI.registerClass(IVarSet.class);

        MineTweakerAPI.registerClass(StringExpansion.class);
        MineTweakerAPI.registerClass(PlayerExpansion.class);
        MineTweakerAPI.registerClass(EntityExpansion.class);
        MineTweakerAPI.registerClass(DimensionExpansion.class);
        MineTweakerAPI.registerClass(ItemStackExpansion.class);

        MineTweakerAPI.registerClass(MyHookEvent.class);
        registerSubclasses(EventWrapper.class);
        registerSubclasses(EventHandlerWrapper.class);

        MineTweakerAPI.registerClass(MTEvents.class);

        MinecraftForge.EVENT_BUS.register(new ForgeEventHook());
        MinecraftForge.EVENT_BUS.register(new VarHandler());
        FMLCommonHandler.instance().bus().register(new FMLEventHook());
        MineTweakerImplementationAPI.onReloadEvent(new IEventHandler<MineTweakerImplementationAPI.ReloadEvent>() {
            @Override
            public void handle(MineTweakerImplementationAPI.ReloadEvent event) {
                EventManager.getInstance().clear();
            }
        });
    }

    private static void registerSubclasses(Class cls) {
        for (Class subclass: cls.getClasses()) {
            for (Annotation annotation : subclass.getAnnotations()) {
                if (annotation instanceof ZenClass) {
                    MineTweakerAPI.registerClass(subclass);
                }
            }
        }
    }

    public static void invokeMTHook(String name, EntityPlayer player, NBTTagCompound nbtTagCompound) {
        MyHookEvent event = (new MyHookEvent()).setName(name).setPlayer(player).setData(nbtTagCompound);
        MTEvents.invokeHook(event);
    }
}
