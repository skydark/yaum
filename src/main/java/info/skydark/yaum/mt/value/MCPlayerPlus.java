package info.skydark.yaum.mt.value;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.type.IEntityLivingBase;
import info.skydark.yaum.mt.type.IPlayerPlus;
import minetweaker.api.entity.IEntity;
import minetweaker.api.util.Position3f;
import minetweaker.api.world.IDimension;
import minetweaker.mc1710.player.MCPlayer;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by skydark on 15-11-19.
 */
public class MCPlayerPlus extends MCPlayer implements IPlayerPlus {
    private final EntityPlayer entityPlayer;

    public EntityPlayer getInternal() {
        return entityPlayer;
    }

    public MCPlayerPlus(EntityPlayer player) {
        super(player);
        entityPlayer = player;
    }

    @Override
    public boolean isChild() {
        return MTHelper.getIEntityLivingBase(entityPlayer).isChild();
    }

    @Override
    public int getAge() {
        return MTHelper.getIEntityLivingBase(entityPlayer).getAge();
    }

    @Override
    public float getHealth() {
        return MTHelper.getIEntityLivingBase(entityPlayer).getHealth();
    }

    @Override
    public float getMaxHealth() {
        return MTHelper.getIEntityLivingBase(entityPlayer).getMaxHealth();
    }

    @Override
    public void setHealth(float hp) {
        MTHelper.getIEntityLivingBase(entityPlayer).setHealth(hp);
    }

    @Override
    public void heal(float hp) {
        MTHelper.getIEntityLivingBase(entityPlayer).heal(hp);
    }

    @Override
    public void mount(IEntity entity) {
        MTHelper.getIEntityLivingBase(entityPlayer).mount(entity);
    }

    @Override
    public int getTotalArmorValue() {
        return MTHelper.getIEntityLivingBase(entityPlayer).getTotalArmorValue();
    }

    @Override
    public void setTarget(IEntityLivingBase iEntity) {
        MTHelper.getIEntityLivingBase(entityPlayer).setTarget(iEntity);
    }

    @Override
    public void addPotionEffect(String name, int duration, int amplifier, boolean isAmbient) {
        MTHelper.getIEntityLivingBase(entityPlayer).addPotionEffect(name, duration, amplifier, isAmbient);
    }

    @Override
    public int nextInt(int n) {
        return MTHelper.getIEntityLivingBase(entityPlayer).nextInt(n);
    }

    @Override
    public float nextFloat() {
        return MTHelper.getIEntityLivingBase(entityPlayer).nextFloat();
    }

    @Override
    public IDimension getDimension() {
        return MTHelper.getIEntity(entityPlayer).getDimension();
    }

    @Override
    public float getX() {
        return MTHelper.getIEntity(entityPlayer).getX();
    }

    @Override
    public float getY() {
        return MTHelper.getIEntity(entityPlayer).getY();
    }

    @Override
    public float getZ() {
        return MTHelper.getIEntity(entityPlayer).getZ();
    }

    @Override
    public Position3f getPosition() {
        return MTHelper.getIEntity(entityPlayer).getPosition();
    }

    @Override
    public void setPosition(Position3f position) {
        MTHelper.getIEntity(entityPlayer).setPosition(position);
    }
}
