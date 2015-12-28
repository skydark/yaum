package info.skydark.yaum.mt.value;

import info.skydark.yaum.mt.MTHelper;
import info.skydark.yaum.mt.type.IMyDamageSource;
import minetweaker.api.entity.IEntity;
import net.minecraft.util.DamageSource;

/**
 * Created by skydark on 15-11-16.
 */
public class MCDamageSource implements IMyDamageSource {
    private DamageSource source;

    public MCDamageSource(DamageSource source) {
        this.source = source;
    }

    @Override
    public String getDamageType() {
        return source.damageType;
    }

    @Override
    public IEntity getEntity() {
        return MTHelper.getIEntity(source.getEntity());
    }
}
