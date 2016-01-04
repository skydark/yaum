package info.skydark.yaum.mt.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.gameevent.*;
import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.expansion.EntityExpansion;
import info.skydark.yaum.mt.type.IEntityLivingBase;
import info.skydark.yaum.mt.type.IMyDamageSource;
import info.skydark.yaum.mt.type.IPlayerPlus;
import minetweaker.MineTweakerAPI;
import minetweaker.api.entity.IEntity;
import minetweaker.api.entity.IEntityItem;
import minetweaker.api.item.IItemStack;
import minetweaker.api.world.IDimension;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;


/**
 * Created by skydark on 15-11-16.
 */
public class EventWrapper {
    @ZenClass("mod.yaum.event.Event")
    public static class MyEvent {
        private final Event event;
        private boolean canceled;

        public MyEvent(Event event) {
            this.event = event;
            canceled = event.isCanceled();
        }

        @ZenMethod
        public void cancel() {
            if (event.isCancelable()) {
                canceled = true;
                event.setCanceled(canceled);
            } else {
                MineTweakerAPI.logError("This event is not cancelable: " + event.toString());
            }
        }

        @ZenGetter("canceled")
        public boolean isCanceled() {
            return canceled;
        }

        @ZenMethod
        public void allow() {
            event.setResult(Event.Result.ALLOW);
        }

        @ZenMethod
        public void deny() {
            event.setResult(Event.Result.DENY);
        }

        @ZenMethod
        public void setDefault() {
            event.setResult(Event.Result.DEFAULT);
        }

        @ZenMethod
        public boolean isResult(String result) {
            return event.getResult().name().equalsIgnoreCase(result);
        }
    }

    @ZenClass("mod.yaum.event.EntityEvent")
    public static class MyEntityEvent extends MyEvent{
        private final Entity entity;

        public MyEntityEvent(EntityEvent event) {
            super(event);
            entity = event.entity;
        }

        @ZenGetter("entity")
        public IEntity getEntity() {
            return MTHelper.getIEntity(entity);
        }
    }

    @ZenClass("mod.yaum.event.LivingEvent")
    public static class MyLivingEvent extends MyEntityEvent {
        private final EntityLivingBase entityLiving;

        public MyLivingEvent(LivingEvent event) {
            super(event);
            this.entityLiving = event.entityLiving;
        }

        @ZenGetter("living")
        public IEntityLivingBase getEntityLiving() {
            return MTHelper.getIEntityLivingBase(entityLiving);
        }
    }

    @ZenClass("mod.yaum.event.PlayerEvent")
    public static class MyPlayerEvent extends MyLivingEvent {
        private final EntityPlayer player;

        public MyPlayerEvent(net.minecraftforge.event.entity.player.PlayerEvent event) {
            super(event);
            player = event.entityPlayer;
        }

        @ZenGetter("player")
        public IPlayerPlus getPlayer() {
            return MTHelper.getIPlayer(player);
        }

        @ZenGetter("world")
        public IDimension getWorld() {
            return MTHelper.getIDimension(player.worldObj);
        }
    }

    @ZenClass("mod.yaum.event.BlockEvent")
    public static class MyBlockEvent extends MyEvent{
        private final BlockEvent eventBlock;
        private final int x;
        private final int y;
        private final int z;
        private final World world;
        private final Block block;
        private final int meta;

        public MyBlockEvent(BlockEvent event) {
            super(event);
            eventBlock = event;
            x = event.x;
            y = event.y;
            z = event.z;
            world = event.world;
            block = event.block;
            meta = event.blockMetadata;
        }

        @ZenGetter("x")
        public int getX() {
            return x;
        }

        @ZenGetter("y")
        public int getY() {
            return y;
        }

        @ZenGetter("z")
        public int getZ() {
            return z;
        }

        @ZenGetter("world")
        public IDimension getWorld() {
            return MTHelper.getIDimension(world);
        }

        @ZenMethod
        public boolean matchBlock(String pattern) {
            return MTHelper.matchBlock(pattern, block, meta);
        }

        @ZenGetter("meta")
        public int getMeta() {
            return meta;
        }
    }

    @ZenClass("mod.yaum.event.BreakEvent")
    public static class MyBreakEvent extends MyBlockEvent {
        public final EntityPlayer player;
        public final BlockEvent.BreakEvent breakEvent;
        public MyBreakEvent(BlockEvent.BreakEvent event) {
            super(event);
            breakEvent = event;
            player = event.getPlayer();
        }

        @ZenGetter("exp")
        public int getExp() {
            return breakEvent.getExpToDrop();
        }

        @ZenSetter("exp")
        public void setExp(int exp) {
            breakEvent.setExpToDrop(exp);
        }

        @ZenGetter("player")
        public IPlayerPlus getPlayer() {
            return MTHelper.getIPlayer(player);
        }
    }

    @ZenClass("mod.yaum.event.PlaceEvent")
    public static class MyPlaceEvent extends MyBlockEvent {
        public final EntityPlayer player;
        public final ItemStack stack;
        public MyPlaceEvent(BlockEvent.PlaceEvent event) {
            super(event);
            player = event.player;
            stack = event.itemInHand;
        }

        @ZenGetter("item")
        public IItemStack getItem() {
            return MTHelper.getIItemStack(stack);
        }

        @ZenGetter("player")
        public IPlayerPlus getPlayer() {
            return MTHelper.getIPlayer(player);
        }
    }

    @ZenClass("mod.yaum.event.PlayerInteractEvent")
    public static class MyPlayerInteractEvent extends MyPlayerEvent {
        private final int x;
        private final int y;
        private final int z;
        private final int face;
        private final String action;
        private final PlayerInteractEvent event;
        public MyPlayerInteractEvent(PlayerInteractEvent event) {
            super(event);
            this.event = event;
            x = event.x;
            y = event.y;
            z = event.z;
            face = event.face;
            action = event.action.name();
        }
        @ZenGetter("action")
        public String  getAction() {
            return action;
        }
        @ZenGetter("x")
        public int getX() {
            return x;
        }
        @ZenGetter("y")
        public int getY() {
            return y;
        }
        @ZenGetter("z")
        public int getZ() {
            return z;
        }
        @ZenGetter("face")
        public int getFace() {
            return face;
        }
        @ZenGetter("useBlock")
        public boolean getUseBlock() {
            return event.useBlock != Event.Result.DENY;
        }
        @ZenGetter("useItem")
        public boolean getUseItem() {
            return event.useItem != Event.Result.DENY;
        }
        public PlayerInteractEvent getInternal() {
            return event;
        }
    }

    @ZenClass("mod.yaum.event.PlayerInteractEntityEvent")
    public static class MyPlayerInteractEntityEvent extends MyPlayerEvent{
        private final Entity target;

        public MyPlayerInteractEntityEvent(EntityInteractEvent event) {
            super(event);
            target = event.target;
        }

        @ZenGetter("target")
        public IEntity getTarget() {
            return MTHelper.getIEntity(target);
        }
    }

    @ZenClass("mod.yaum.event.PlayerAttackEntityEvent")
    public static class MyPlayerAttackEntityEvent extends MyPlayerEvent {
        private final Entity target;

        public MyPlayerAttackEntityEvent(AttackEntityEvent event) {
            super(event);
            target = event.target;
        }

        @ZenGetter("target")
        public IEntity getTarget() {
            return MTHelper.getIEntity(target);
        }

        @ZenGetter("targetLiving")
        public IEntityLivingBase getTargetLiving() {
            return EntityExpansion.asLivingBase(getTarget());
        }
    }

    @ZenClass("mod.yaum.event.PlayerChangedDimensionEvent")
    public static class MyPlayerChangedDimensionEvent {
        private final IPlayerPlus player;
        private final IDimension from;
        private final IDimension to;

        public MyPlayerChangedDimensionEvent(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
            this.player = MTHelper.getIPlayer(event.player);
            this.from = MTHelper.getIDimension(event.fromDim);
            this.to = MTHelper.getIDimension(event.toDim);
        }

        @ZenGetter("player")
        public IPlayerPlus getPlayer() {
            return player;
        }

        @ZenGetter("from")
        public IDimension getFrom() {
            return from;
        }

        @ZenGetter("to")
        public IDimension getTo() {
            return to;
        }
    }

    @ZenClass("mod.yaum.event.LivingDeathEvent")
    public static class MyLivingDeathEvent extends MyLivingEvent{
        private final IMyDamageSource source;

        public MyLivingDeathEvent(LivingDeathEvent event) {
            super(event);
            this.source = MTHelper.getIMyDamageSource(event.source);
        }

        @ZenGetter("source")
        public IMyDamageSource getDamageSource() {
            return source;
        }
    }

    @ZenClass("mod.yaum.event.LivingDropsEvent")
    public static class MyLivingDropsEvent extends MyLivingEvent{
        // TODO: modify drops
        private final IMyDamageSource source;
        private final int lootingLevel;
        private final boolean recentlyHit;
        private final int specialDropValue;
        private final LivingDropsEvent event;

        public MyLivingDropsEvent(LivingDropsEvent ev) {
            super(ev);
            event = ev;
            source = MTHelper.getIMyDamageSource(ev.source);
            lootingLevel = ev.lootingLevel;
            recentlyHit = ev.recentlyHit;
            specialDropValue = ev.specialDropValue;
        }

        @ZenGetter("source")
        public IMyDamageSource getDamageSource() {
            return source;
        }

        @ZenGetter("drops")
        public IEntityItem[] getDrops() {
            IEntityItem[] drops = new IEntityItem[event.drops.size()];
            for (int i = 0; i < event.drops.size(); i++) {
                drops[i] = MTHelper.getIEntityItem(event.drops.get(i));
            }
            return drops;
        }

        @ZenGetter("lootingLevel")
        public int getLootingLevel() {
            return lootingLevel;
        }

        @ZenGetter("recentlyHit")
        public boolean getRecentlyHit() {
            return recentlyHit;
        }

        @ZenGetter("specialDropValue")
        public int getSpecialDropValue() {
            return specialDropValue;
        }

        @ZenMethod
        public void clear() {
            event.drops.clear();
        }

        @ZenMethod
        public void addDrop(IItemStack stack) {
            EntityLivingBase entity = MTHelper.getEntityLivingBase(getEntityLiving());
            ItemStack _stack = MTHelper.getItemStack(stack);
            if (entity != null && _stack != null) {
                event.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, _stack));
            }
        }

        public LivingDropsEvent getInternal() {
            return event;
        }
    }

    @ZenClass("mod.yaum.event.EnderTeleportEvent")
    public static class MyEnderTeleportEvent extends MyLivingEvent{
        private double x;
        private double y;
        private double z;
        private float damage;

        public MyEnderTeleportEvent(EnderTeleportEvent ev) {
            super(ev);
            x = ev.targetX;
            y = ev.targetY;
            z = ev.targetZ;
            damage = ev.attackDamage;
        }

        @ZenGetter("x")
        public double getX() {
            return x;
        }

        @ZenGetter("y")
        public double getY() {
            return y;
        }

        @ZenGetter("z")
        public double getZ() {
            return z;
        }

        @ZenGetter("damage")
        public float getDamage() {
            return damage;
        }

        @ZenSetter("x")
        public void setX(double x) {
            this.x = x;
        }

        @ZenSetter("y")
        public void setY(double y) {
            this.y = y;
        }

        @ZenSetter("z")
        public void setZ(double z) {
            this.z = z;
        }

        @ZenSetter("damage")
        public void setDamage(float damage) {
            this.damage = damage;
        }
    }

    @ZenClass("mod.yaum.event.ItemPickupEvent")
    public static class MyItemPickupEvent extends MyPlayerEvent{
        private final EntityItem item;
        public MyItemPickupEvent(EntityItemPickupEvent ev) {
            super(ev);
            item = ev.item;
        }

        @ZenGetter("item")
        public IItemStack getItem() {
            return MTHelper.getIItemStack(item.getEntityItem());
        }

        @ZenSetter("item")
        public void setItem(IItemStack stack) {
            ItemStack itemStack = MTHelper.getItemStack(stack);
            if (itemStack != null) {
                item.setEntityItemStack(itemStack);
            }
        }
    }

    @ZenClass("mod.yaum.event.ItemCraftedEvent")
    public static class MyItemCraftedEvent {
        private final EntityPlayer player;
        private final ItemStack itemStack;

        public MyItemCraftedEvent(cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent ev) {
            player = ev.player;
            itemStack = ev.crafting;
            // ev.craftMatrix
        }

        @ZenGetter("player")
        public IPlayerPlus getPlayer() {
            return MTHelper.getIPlayer(player);
        }

        @ZenGetter("item")
        public IItemStack getItem() {
            return MTHelper.getIItemStack(itemStack);
        }
    }

    @ZenClass("mod.yaum.event.CheckSpawnEvent")
    public static class MyCheckSpawnEvent extends MyLivingEvent{
        private final World world;
        private final float x;
        private final float y;
        private final float z;
        private final LivingSpawnEvent.CheckSpawn event;

        public MyCheckSpawnEvent(LivingSpawnEvent.CheckSpawn ev) {
            super(ev);
            event = ev;
            x = ev.x;
            y = ev.y;
            z = ev.z;
            world = ev.world;
        }

        @ZenGetter("x")
        public float getX() {
            return x;
        }

        @ZenGetter("y")
        public float getY() {
            return y;
        }

        @ZenGetter("z")
        public float getZ() {
            return z;
        }

        @ZenGetter("world")
        public IDimension getWorld() {
            return MTHelper.getIDimension(world);
        }

        @ZenMethod
        public boolean matchFootBlock(String pattern) {
            Block block = world.getBlock((int) x, (int) y - 1, (int) z);
            int meta = world.getBlockMetadata((int) x, (int) y - 1, (int) z);
            return MTHelper.matchBlock(pattern, block, meta);
        }
    }

    @ZenClass("mod.yaum.Event.AchievementEvent")
    public static class MyAchievementEvent extends MyPlayerEvent {
        private final Achievement achievement;

        public MyAchievementEvent(AchievementEvent event) {
            super(event);
            achievement = event.achievement;
        }

        @ZenGetter("id")
        public String getId() {
            return achievement.statId;
        }

        public Achievement getAchievement() {
            return achievement;
        }
    }
}
