package info.skydark.yaum.mt.event;

import info.skydark.yaum.mt.type.IPlayerPlus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skydark on 15-11-16.
 */
public class EventManager {
    private static EventManager instance = new EventManager();
    private List<EventModule> eventModules;

    public final EventModule<EventWrapper.MyPlayerInteractEvent> playerInteract;
    public final EventModule<EventWrapper.MyPlayerInteractEntityEvent> playerInteractEntity;
    public final EventModule<EventWrapper.MyPlayerAttackEntityEvent> playerAttackEntity;
    public final EventModule<EventWrapper.MyPlayerChangedDimensionEvent> playerChangedDimension;
    public final EventModule<EventWrapper.MyLivingDeathEvent> livingDeath;
    public final EventModule<EventWrapper.MyLivingDropsEvent> livingDrops;
    public final EventModule<EventWrapper.MyPlaceEvent> placeBlock;
    public final EventModule<EventWrapper.MyBreakEvent> breakBlock;
    public final EventModule<EventWrapper.MyEnderTeleportEvent> enderTeleport;
    public final EventModule<EventWrapper.MyCheckSpawnEvent> checkSpawn;
    public final EventModule<EventWrapper.MyItemPickupEvent> itemPickup;
    public final EventModule<EventWrapper.MyItemCraftedEvent> itemCrafted;
    public final EventModule<EventWrapper.MyAchievementEvent> achievement;
    public final EventModule<IPlayerPlus> playerLogin;
    public final EventModule<IPlayerPlus> playerRespawn;

    private EventManager(){
        eventModules = new ArrayList<EventModule>();
        playerInteract = new EventModule<EventWrapper.MyPlayerInteractEvent>();
        playerInteractEntity = new EventModule<EventWrapper.MyPlayerInteractEntityEvent>();
        playerAttackEntity = new EventModule<EventWrapper.MyPlayerAttackEntityEvent>();
        playerChangedDimension = new EventModule<EventWrapper.MyPlayerChangedDimensionEvent>();
        livingDeath = new EventModule<EventWrapper.MyLivingDeathEvent>();
        livingDrops = new EventModule<EventWrapper.MyLivingDropsEvent>();
        placeBlock = new EventModule<EventWrapper.MyPlaceEvent>();
        breakBlock = new EventModule<EventWrapper.MyBreakEvent>();
        enderTeleport = new EventModule<EventWrapper.MyEnderTeleportEvent>();
        checkSpawn = new EventModule<EventWrapper.MyCheckSpawnEvent>();
        itemPickup = new EventModule<EventWrapper.MyItemPickupEvent>();
        itemCrafted = new EventModule<EventWrapper.MyItemCraftedEvent>();
        achievement = new EventModule<EventWrapper.MyAchievementEvent>();
        playerLogin = new EventModule<IPlayerPlus>();
        playerRespawn = new EventModule<IPlayerPlus>();
        eventModules.add(playerInteract);
        eventModules.add(playerInteractEntity);
        eventModules.add(playerAttackEntity);
        eventModules.add(playerChangedDimension);
        eventModules.add(livingDeath);
        eventModules.add(livingDrops);
        eventModules.add(placeBlock);
        eventModules.add(breakBlock);
        eventModules.add(enderTeleport);
        eventModules.add(checkSpawn);
        eventModules.add(itemPickup);
        eventModules.add(itemCrafted);
        eventModules.add(playerLogin);
        eventModules.add(playerRespawn);
        eventModules.add(achievement);
    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void clear() {
        for (EventModule module: eventModules) {
            module.clear();
        }
    }
}
