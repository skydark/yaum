package info.skydark.yaum.dispenser;

import cpw.mods.fml.relauncher.ReflectionHelper;
import info.skydark.yaum.Config;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.util.RegistrySimple;

import java.util.Map;

/**
 * Created by skydark on 15-11-23.
 */
public class DispenserAddons {
    public static void init() {
        if (Config.dispenserFillCauldron) {
            Map registry = ReflectionHelper.getPrivateValue(RegistrySimple.class, (RegistrySimple) BlockDispenser.dispenseBehaviorRegistry, "registryObjects", "field_82596_a");
            registry.put(Items.water_bucket, new BehaviorWaterBucket());
        }
    }
}
