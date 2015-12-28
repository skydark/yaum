package info.skydark.yaum.mt.value;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.type.IEntityLivingBase;
import minetweaker.api.entity.IEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-11-16.
 */
public class MCEntityLivingBase extends MCEntity implements IEntityLivingBase {
    private EntityLivingBase entityLivingBase;
    public MCEntityLivingBase(EntityLivingBase entityLivingBase) {
        super(entityLivingBase);
        this.entityLivingBase = entityLivingBase;
    }

    @Override
    public EntityLivingBase getInternal() {
        return entityLivingBase;
    }

    @Override
    public boolean isChild() {
        return entityLivingBase == null ? false : entityLivingBase.isChild();
    }

    @Override
    public float getHealth() {
        return entityLivingBase == null ? 0 :entityLivingBase.getHealth();
    }

    @Override
    public float getMaxHealth() {
        return entityLivingBase == null ? 0 : entityLivingBase.getMaxHealth();
    }

    @Override
    public void setHealth(float hp) {
        if (entityLivingBase != null) entityLivingBase.setHealth(hp);
    }

    @Override
    public void heal(float hp) {
        if (entityLivingBase != null) entityLivingBase.heal(hp);
    }

    @Override
    public int getAge() {
        return entityLivingBase == null ? 0 :entityLivingBase.getAge();
    }

    @Override
    public void mount(IEntity iEntity) {
        Entity entity = MTHelper.getEntity(iEntity);
        if (entityLivingBase != null && entity != null)
            entityLivingBase.mountEntity(entity);
    }

    @Override
    public int getTotalArmorValue() {
        return entityLivingBase == null ? 0 : entityLivingBase.getTotalArmorValue();
    }

    @Override
    public void setTarget(IEntityLivingBase iEntity) {
        EntityLivingBase entity = MTHelper.getEntityLivingBase(iEntity);
        if (entityLivingBase != null && entity != null) {
            entityLivingBase.setRevengeTarget(entity);
            if (entityLivingBase instanceof EntityLiving) {
                ((EntityLiving) entityLivingBase).setAttackTarget(entity);
                if (entityLivingBase instanceof EntityCreature) {
                    ((EntityCreature) entityLivingBase).setTarget(entity);
                }
            }
        }
    }

    @Override
    public int nextInt(int n) {
        if (entityLivingBase == null) return -1;
        return n == 0 ? entityLivingBase.getRNG().nextInt() : entityLivingBase.getRNG().nextInt(n);
    }

    @Override
    public float nextFloat() {
        return entityLivingBase == null ? -1 : entityLivingBase.getRNG().nextFloat();
    }
}
