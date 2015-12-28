package info.skydark.yaum.mt.value;

import info.skydark.yaum.mt.MTHelper;
import minetweaker.api.entity.IEntityItem;
import minetweaker.api.item.IItemStack;
import net.minecraft.entity.item.EntityItem;

/**
 * Created by skydark on 15-11-16.
 */
public class MCEntityItem extends MCEntity implements IEntityItem {
    private final EntityItem entityItem;

    public MCEntityItem(EntityItem entity) {
        super(entity);
        entityItem = entity;
    }

    @Override
    public IItemStack getItem() {
        return MTHelper.getIItemStack(entityItem.getEntityItem());
    }
}
