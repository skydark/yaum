package info.skydark.yaum.mt.var;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Created by skydark on 16-1-14.
 */
public class VarHandler {
    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            VarProperties.register((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        NBTTagCompound temp = new NBTTagCompound();
        VarProperties.getVarProperties(event.original).saveNBTData(temp);
        VarProperties.getVarProperties(event.entityPlayer).loadNBTData(temp);
    }
}
