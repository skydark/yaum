package info.skydark.yaum.mt.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Created by skydark on 15-11-15.
 */
public class ForgeEventHook {
    private final EventManager eventManager = EventManager.getInstance();

    @SubscribeEvent
    public void onAchievement(AchievementEvent ev) {
        eventManager.achievement.publish(new EventWrapper.MyAchievementEvent(ev));
    }

    @SubscribeEvent
    public void onPlaceBlock(BlockEvent.PlaceEvent ev) {
        eventManager.placeBlock.publish(new EventWrapper.MyPlaceEvent(ev));
    }

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent ev) {
        eventManager.breakBlock.publish(new EventWrapper.MyBreakEvent(ev));
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent ev) {
        eventManager.playerInteract.publish(new EventWrapper.MyPlayerInteractEvent(ev));
    }

    @SubscribeEvent
    public void onPlayerInteractEntity(EntityInteractEvent ev) {
        // if (ev.entity.worldObj.isRemote) return;
        eventManager.playerInteractEntity.publish(new EventWrapper.MyPlayerInteractEntityEvent(ev));
    }

    @SubscribeEvent
    public void onPlayerAttackEntity(AttackEntityEvent ev) {
        // if (ev.entity.worldObj.isRemote) return;
        eventManager.playerAttackEntity.publish(new EventWrapper.MyPlayerAttackEntityEvent(ev));
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent ev) {
        // if (ev.entity.worldObj.isRemote) return;
        eventManager.livingDeath.publish(new EventWrapper.MyLivingDeathEvent(ev));
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onLivingDrop(LivingDropsEvent ev) {
        eventManager.livingDrops.publish(new EventWrapper.MyLivingDropsEvent(ev));
    }

    @SubscribeEvent
    public void onEnderTeleport(EnderTeleportEvent ev) {
        eventManager.enderTeleport.publish(new EventWrapper.MyEnderTeleportEvent(ev));
    }

    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn ev) {
        eventManager.checkSpawn.publish(new EventWrapper.MyCheckSpawnEvent(ev));
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent ev) {
        eventManager.itemPickup.publish(new EventWrapper.MyItemPickupEvent(ev));
    }
}