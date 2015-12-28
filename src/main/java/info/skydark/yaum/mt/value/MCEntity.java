package info.skydark.yaum.mt.value;

import info.skydark.yaum.mt.MTHelper;
import minetweaker.api.entity.IEntity;
import minetweaker.api.util.Position3f;
import minetweaker.api.world.IDimension;
import net.minecraft.entity.Entity;

/**
 * Created by skydark on 15-11-15.
 */
public class MCEntity implements IEntity {
    private Entity entity;

    public MCEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public IDimension getDimension() {
        return MTHelper.getIDimension(entity.worldObj);
    }

    @Override
    public float getX() {
        return (float) entity.posX;
    }

    @Override
    public float getY() {
        return (float) entity.posY;
    }

    @Override
    public float getZ() {
        return (float) entity.posZ;
    }

    @Override
    public Position3f getPosition(){
        return new Position3f(getX(), getY(), getZ());
    }

    @Override
    public void setPosition(Position3f position){
        entity.setPosition(position.getX(), position.getY(), position.getZ());
    }

    public Entity getInternal() {
        return entity;
    }
}
