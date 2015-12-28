package info.skydark.yaum.mt.type;

import minetweaker.api.damage.IDamageSource;
import minetweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * Created by skydark on 15-11-16.
 */
@ZenClass("mod.yaum.type.IDamageSource")
public interface IMyDamageSource extends IDamageSource {
    @ZenGetter("damageType")
    public String getDamageType();

    @ZenGetter("entity")
    public IEntity getEntity();
}
