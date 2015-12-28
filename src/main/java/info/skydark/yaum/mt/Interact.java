package info.skydark.yaum.mt;

import info.skydark.yaum.mt.event.EventHandlerWrapper;
import info.skydark.yaum.mt.event.EventManager;
import info.skydark.yaum.mt.event.EventWrapper;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.util.IEventHandler;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-11-21.
 */
@ZenClass("mod.yaum.Interact")
public class Interact {
    private static void interactHandler(EventWrapper.MyPlayerInteractEvent event, IIngredient hand, String block, EventHandlerWrapper.IPlayerInteractEventHandler handler) {
        if (hand == null || block == null || handler == null) return;
        if (!hand.matches(event.getPlayer().getCurrentItem())) return;
        World world = event.getInternal().world;
        int x = event.getX();
        int y = event.getY();
        int z = event.getZ();
        if (!MTHelper.matchBlock(block, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z))) return;
        try {
            handler.handle(event);
        } catch (Throwable e) {
            MineTweakerAPI.logError("Exception during handling event", e);
        }
    }

    @ZenMethod
    public static void left(final IIngredient hand, final String block, final EventHandlerWrapper.IPlayerInteractEventHandler handler) {
        EventManager.getInstance().playerInteract.on(new IEventHandler<EventWrapper.MyPlayerInteractEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerInteractEvent event) {
                if (!"LEFT_CLICK_BLOCK".equals(event.getAction())) return;
                interactHandler(event, hand, block, handler);
            }
        });
    }

    @ZenMethod
    public static void right(final IIngredient hand, final String block, final EventHandlerWrapper.IPlayerInteractEventHandler handler) {
        EventManager.getInstance().playerInteract.on(new IEventHandler<EventWrapper.MyPlayerInteractEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerInteractEvent event) {
                if (!"RIGHT_CLICK_BLOCK".equals(event.getAction())) return;
                interactHandler(event, hand, block, handler);
            }
        });
    }

    @ZenMethod
    public static void both(final IIngredient hand, final String block, final EventHandlerWrapper.IPlayerInteractEventHandler handler) {
        EventManager.getInstance().playerInteract.on(new IEventHandler<EventWrapper.MyPlayerInteractEvent>() {
            @Override
            public void handle(EventWrapper.MyPlayerInteractEvent event) {
                if (!"LEFT_CLICK_BLOCK".equals(event.getAction()) && !"RIGHT_CLICK_BLOCK".equals(event.getAction())) return;
                interactHandler(event, hand, block, handler);
            }
        });
    }

    @ZenMethod
    public static void place(final IIngredient ingredient, final EventHandlerWrapper.IPlaceEventHandler handler) {
        if (ingredient == null || handler == null) {
            MineTweakerAPI.logError("invalid null argument");
            return;
        }
        EventManager.getInstance().placeBlock.on(new IEventHandler<EventWrapper.MyPlaceEvent>() {
            @Override
            public void handle(EventWrapper.MyPlaceEvent event) {
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
    public static void breakit(final String block, final EventHandlerWrapper.IBreakEventHandler handler) {
        if (block == null || handler == null) {
            MineTweakerAPI.logError("invalid null argument");
            return;
        }
        EventManager.getInstance().breakBlock.on(new IEventHandler<EventWrapper.MyBreakEvent>() {
            @Override
            public void handle(EventWrapper.MyBreakEvent event) {
                if (!event.matchBlock(block)) return;
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    MineTweakerAPI.logError("Exception during handling event", e);
                }
            }
        });
    }
}
