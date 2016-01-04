package info.skydark.yaum.mt.event;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.type.IPlayerPlus;
import minetweaker.api.data.IData;
import minetweaker.api.entity.IEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * Created by skydark on 16-1-4.
 */

@ZenClass("mod.yaum.Event.HookEvent")
public class MyHookEvent {
    private String name = "";
    private boolean result = false;
    private IPlayerPlus player = null;
    private IData data = null;
    private IEntity entity = null;

    public MyHookEvent() {}

    @ZenGetter("name")
    public String getName() {
        return name;
    }

    public MyHookEvent setName(String name) {
        this.name = name;
        return this;
    }

    @ZenGetter("result")
    public boolean getResult() {
        return result;
    }

    @ZenSetter("result")
    public MyHookEvent setResult(boolean result) {
        this.result = result;
        return this;
    }

    @ZenGetter("player")
    public IPlayerPlus getPlayer() {
        return player;
    }

    public MyHookEvent setPlayer(EntityPlayer player) {
        this.player = MTHelper.getIPlayer(player);
        return this;
    }

    @ZenGetter("data")
    public IData getData() {
        return data;
    }

    public MyHookEvent setData(NBTBase nbt) {
        data = MTHelper.getIData(nbt);
        return this;
    }

    @ZenGetter("entity")
    public IEntity getEntity() {
        return entity;
    }

    public MyHookEvent setEntity(Entity entity) {
        this.entity = MTHelper.getIEntity(entity);
        return this;
    }
}
