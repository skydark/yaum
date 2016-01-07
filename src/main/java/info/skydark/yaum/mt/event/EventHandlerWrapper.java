package info.skydark.yaum.mt.event;

import info.skydark.yaum.mt.type.IPlayerPlus;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * Created by skydark on 15-11-16.
 */
public class EventHandlerWrapper {
    @ZenClass("mod.yaum.IEventHandler.IAchievement")
    public interface IAchievementHandler {
        public void handle(EventWrapper.MyAchievementEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IAchievementPlayer")
    public interface IAchievementPlayerHandler {
        public void handle(IPlayerPlus player);
    }

    @ZenClass("mod.yaum.IPlayerHandler")
    public interface IPlayerHandler {
        public void handle(IPlayerPlus iPlayer);
    }

    @ZenClass("mod.yaum.IEventHandler.IPlaceBlock")
    public interface IPlaceEventHandler {
        public void handle(EventWrapper.MyPlaceEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IBreakBlock")
    public interface IBreakEventHandler {
        public void handle(EventWrapper.MyBreakEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IPlayerInteract")
    public interface IPlayerInteractEventHandler {
        public void handle(EventWrapper.MyPlayerInteractEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IPlayerChangedDimension")
    public interface IPlayerChangedDimensionEventHandler {
        public void handle(EventWrapper.MyPlayerChangedDimensionEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IPlayerInteractEntity")
    public interface IPlayerInteractEntityEventHandler {
        public void handle(EventWrapper.MyPlayerInteractEntityEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IPlayerAttackEntityEventHandler")
    public interface IPlayerAttackEntityEventHandler {
        public void handle(EventWrapper.MyPlayerAttackEntityEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.ILivingDeathEventHandler")
    public interface ILivingDeathEventHandler {
        public void handle(EventWrapper.MyLivingDeathEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.ILivingDropsEventHandler")
    public interface ILivingDropsEventHandler {
        public void handle(EventWrapper.MyLivingDropsEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IEnderTeleport")
    public interface IEnderTeleportEventHandler {
        public void handle(EventWrapper.MyEnderTeleportEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.ICheckSpawn")
    public interface ICheckSpawnEventHandler {
        public void handle(EventWrapper.MyCheckSpawnEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IDisableSpawn")
    public interface IDisableSpawnEventHandler {
        public boolean handle(EventWrapper.MyCheckSpawnEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IItemPickup")
    public interface IItemPickupEventHandler {
        public void handle(EventWrapper.MyItemPickupEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IItemCrafted")
    public interface IItemCraftedEventHandler {
        public void handle(EventWrapper.MyItemCraftedEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IPlayerTick")
    public interface IPlayerTickHandler {
        public void handle(EventWrapper.MyPlayerTickEvent event);
    }

    @ZenClass("mod.yaum.IEventHandler.IYaumHook")
    public interface IYaumHookHandler {
        public void handle(MyHookEvent event);
    }
}
