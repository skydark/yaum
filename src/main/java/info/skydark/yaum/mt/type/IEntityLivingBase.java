package info.skydark.yaum.mt.type;

import minetweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.*;

/**
 * Created by skydark on 15-11-16.
 */
@ZenClass("mod.yaum.type.IEntityLivingBase")
public interface IEntityLivingBase extends IEntity {
    @ZenGetter
    public boolean isChild();

    @ZenGetter("age")
    public int getAge();

    @ZenGetter("health")
    public float getHealth();

    @ZenSetter("health")
    public void setHealth(float hp);

    @ZenGetter("maxHealth")
    public float getMaxHealth();

    @ZenMethod
    public void heal(float hp);

    @ZenMethod
    public void mount(IEntity entity);

    @ZenGetter("totalArmorValue")
    public int getTotalArmorValue();

    @ZenMethod
    public void setTarget(IEntityLivingBase entity);

    @ZenMethod
    public void addPotionEffect(String name, int duration, int amplifier, @Optional boolean isAmbient);

    @ZenMethod
    public int nextInt(@Optional int n);

    @ZenMethod
    public float nextFloat();
}
