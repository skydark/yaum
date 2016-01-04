package info.skydark.yaum.mt.event;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.type.IPlayerPlus;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import minetweaker.util.IEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.Achievement;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-11-15.
 */
@ZenClass("mod.yaum.Events")
public class MTEvents {
    @ZenMethod
    public static void registerHook(final String id, final EventHandlerWrapper.IYaumHookHandler handler) {
        if (id == null || handler == null) return;
        EventManager.getInstance().yaumHook.on(new IEventHandler<MyHookEvent>() {
            @Override
            public void handle(MyHookEvent event) {
                if (id.equals("*") || id.equals(event.getName())) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void invokeHook(MyHookEvent event) {
        EventManager.getInstance().yaumHook.publish(event);
    }

    @ZenMethod
    public static void onAchievement(String id, final EventHandlerWrapper.IAchievementPlayerHandler handler) {
        if (id == null || handler == null) return;
        final String _id = "achievement." + id;
        EventManager.getInstance().achievement.on(new IEventHandler<EventWrapper.MyAchievementEvent>() {
            @Override
            public void handle(EventWrapper.MyAchievementEvent event) {
                if (_id.equals(event.getId())) {
                    EntityPlayer player = MTHelper.getPlayer(event.getPlayer());
                    if (!(player instanceof EntityPlayerMP)) return;
                    EntityPlayerMP playerMP = (EntityPlayerMP) player;
                    Achievement achievement = event.getAchievement();
                    if (playerMP.func_147099_x().writeStat(achievement) > 0) return;
                    if (playerMP.func_147099_x().canUnlockAchievement(event.getAchievement())) {
                        try {
                            handler.handle(event.getPlayer());
                        } catch (Throwable e) {
                            MineTweakerAPI.logError("Exception during handling event", e);
                        }
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerLogin(final EventHandlerWrapper.IPlayerHandler handler) {
        EventManager.getInstance().playerLogin.on(new IEventHandler<IPlayerPlus>() {
            @Override
            public void handle(IPlayerPlus player) {
                try {
                    handler.handle(player);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerRespawn(final EventHandlerWrapper.IPlayerHandler handler) {
        EventManager.getInstance().playerRespawn.on(new IEventHandler<IPlayerPlus>() {
            @Override
            public void handle(IPlayerPlus player) {
                try {
                    handler.handle(player);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onItemPickup(final IIngredient ingredient, final EventHandlerWrapper.IItemPickupEventHandler handler) {
        if (ingredient == null) return;
        EventManager.getInstance().itemPickup.on(new IEventHandler<EventWrapper.MyItemPickupEvent>() {
            @Override
            public void handle(EventWrapper.MyItemPickupEvent event) {
                if (ingredient.matches(event.getItem())) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onItemCrafted(final IIngredient ingredient, final EventHandlerWrapper.IItemCraftedEventHandler handler) {
        if (ingredient == null) return;
        EventManager.getInstance().itemCrafted.on(new IEventHandler<EventWrapper.MyItemCraftedEvent>() {
            @Override
            public void handle(EventWrapper.MyItemCraftedEvent event) {
                if (ingredient.matches(event.getItem())) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void disableSpawn(final String name, final int dim, @Optional final EventHandlerWrapper.IDisableSpawnEventHandler handler) {
        EventManager.getInstance().checkSpawn.on(new IEventHandler<EventWrapper.MyCheckSpawnEvent>() {
            @Override
            public void handle(EventWrapper.MyCheckSpawnEvent event) {
                if (MTHelper.matchEntity(name, event.getEntityLiving()) &&
                        (dim == 65536 || dim == MTHelper.getDimensionId(event.getWorld())) &&
                        event.isResult("Default")) {
                    boolean check = false;
                    if (handler == null) {
                        check = true;
                    } else {
                        try {
                            check = handler.handle(event);
                        } catch (Throwable e) {
                            MineTweakerAPI.logError("Exception during handling event", e);
                        }
                    }
                    if (check) {
                        event.deny();
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onCheckSpawn(final String name, final EventHandlerWrapper.ICheckSpawnEventHandler handler) {
        EventManager.getInstance().checkSpawn.on(new IEventHandler<EventWrapper.MyCheckSpawnEvent>() {
            @Override
            public void handle(EventWrapper.MyCheckSpawnEvent event) {
                if (MTHelper.matchEntity(name, event.getEntityLiving())) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onCheckSpawn(final EventHandlerWrapper.ICheckSpawnEventHandler handler) {
        EventManager.getInstance().checkSpawn.on(new IEventHandler<EventWrapper.MyCheckSpawnEvent>() {
            @Override
            public void handle(EventWrapper.MyCheckSpawnEvent event) {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onEnderTeleport(final EventHandlerWrapper.IEnderTeleportEventHandler handler) {
        EventManager.getInstance().enderTeleport.on(new IEventHandler<EventWrapper.MyEnderTeleportEvent>() {
            @Override
            public void handle(EventWrapper.MyEnderTeleportEvent event) {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerInteract(final EventHandlerWrapper.IPlayerInteractEventHandler handler) {
        EventManager.getInstance().playerInteract.on(new IEventHandler<EventWrapper.MyPlayerInteractEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerInteractEvent event) {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerInteractEntity(final EventHandlerWrapper.IPlayerInteractEntityEventHandler handler) {
        EventManager.getInstance().playerInteractEntity.on(new IEventHandler<EventWrapper.MyPlayerInteractEntityEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerInteractEntityEvent event) {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerInteractEntity(final String name, final EventHandlerWrapper.IPlayerInteractEntityEventHandler handler) {
        EventManager.getInstance().playerInteractEntity.on(new IEventHandler<EventWrapper.MyPlayerInteractEntityEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerInteractEntityEvent event) {
                if (MTHelper.matchEntity(name, event.getTarget())) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerInteractEntity(final IIngredient ingredient, final String name, final EventHandlerWrapper.IPlayerInteractEntityEventHandler handler) {
        if (ingredient != null) {
            EventManager.getInstance().playerInteractEntity.on(new IEventHandler<EventWrapper.MyPlayerInteractEntityEvent>() {
                @Override
                public void handle(EventWrapper.MyPlayerInteractEntityEvent event) {
                    IPlayer player = event.getPlayer();
                    if (player == null) return;
                    IItemStack hand = player.getCurrentItem();
                    if (MTHelper.matchEntity(name, event.getTarget()) && ingredient.matches(hand)) {
                        try {
                            handler.handle(event);
                        } catch (Throwable e) {
                            MineTweakerAPI.logError("Exception during handling event", e);
                        }
                    }
                }
            });
        }
    }

    @ZenMethod
    public static void onPlayerAttackEntity(final EventHandlerWrapper.IPlayerAttackEntityEventHandler handler) {
        EventManager.getInstance().playerAttackEntity.on(new IEventHandler<EventWrapper.MyPlayerAttackEntityEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerAttackEntityEvent event) {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerAttackEntity(final String name, final EventHandlerWrapper.IPlayerAttackEntityEventHandler handler) {
        EventManager.getInstance().playerAttackEntity.on(new IEventHandler<EventWrapper.MyPlayerAttackEntityEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerAttackEntityEvent event) {
                if (MTHelper.matchEntity(name, event.getTarget())) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerChangedDimension(final int from, final int to, final EventHandlerWrapper.IPlayerChangedDimensionEventHandler handler) {
        EventManager.getInstance().playerChangedDimension.on(new IEventHandler<EventWrapper.MyPlayerChangedDimensionEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerChangedDimensionEvent event) {
                if (MTHelper.getDimensionId(event.getFrom()) == from && MTHelper.getDimensionId(event.getTo()) == to) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerChangedDimensionFrom(final int from, final EventHandlerWrapper.IPlayerChangedDimensionEventHandler handler) {
        EventManager.getInstance().playerChangedDimension.on(new IEventHandler<EventWrapper.MyPlayerChangedDimensionEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerChangedDimensionEvent event) {
                if (MTHelper.getDimensionId(event.getFrom()) == from) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerChangedDimensionTo(final int to, final EventHandlerWrapper.IPlayerChangedDimensionEventHandler handler) {
        EventManager.getInstance().playerChangedDimension.on(new IEventHandler<EventWrapper.MyPlayerChangedDimensionEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerChangedDimensionEvent event) {
                if (MTHelper.getDimensionId(event.getTo()) == to) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onPlayerChangedDimension(final EventHandlerWrapper.IPlayerChangedDimensionEventHandler handler) {
        EventManager.getInstance().playerChangedDimension.on(new IEventHandler<EventWrapper.MyPlayerChangedDimensionEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerChangedDimensionEvent event) {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onLivingDeath(final EventHandlerWrapper.ILivingDeathEventHandler handler) {
        EventManager.getInstance().livingDeath.on(new IEventHandler<EventWrapper.MyLivingDeathEvent>() {
            @Override
            public void handle(EventWrapper.MyLivingDeathEvent event) {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }

    @ZenMethod
    public static void onLivingDeath(final String name, final EventHandlerWrapper.ILivingDeathEventHandler handler) {
        EventManager.getInstance().livingDeath.on(new IEventHandler<EventWrapper.MyLivingDeathEvent>() {
            @Override
            public void handle(EventWrapper.MyLivingDeathEvent event) {
                if (MTHelper.matchEntity(name, event.getEntityLiving())) {
                    try {
                        handler.handle(event);
                    } catch (Throwable e) {
                        MineTweakerAPI.logError("Exception during handling event", e);
                    }
                }
            }
        });
    }

    @ZenMethod
    public static void onLivingDrop(final EventHandlerWrapper.ILivingDropsEventHandler handler) {
        EventManager.getInstance().livingDrops.on(new IEventHandler<EventWrapper.MyLivingDropsEvent>() {
            @Override
            public void handle(EventWrapper.MyLivingDropsEvent event) {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }
}
