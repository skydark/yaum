package info.skydark.yaum.mt.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import info.skydark.yaum.mt.MTHelper;

/**
 * Created by skydark on 15-11-15.
 */
public class FMLEventHook {
    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent ev) {
        EventManager.getInstance().playerChangedDimension.publish(
                new EventWrapper.MyPlayerChangedDimensionEvent(ev));
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent ev) {
        EventManager.getInstance().playerLogin.publish(MTHelper.getIPlayer(ev.player));
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent ev) {
        EventManager.getInstance().playerRespawn.publish(MTHelper.getIPlayer(ev.player));
    }

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent ev) {
        EventManager.getInstance().itemCrafted.publish(new EventWrapper.MyItemCraftedEvent(ev));
    }
}
