package info.skydark.yaum;

import cpw.mods.fml.common.registry.GameRegistry;
import info.skydark.yaum.block.OnceSpawner;
import info.skydark.yaum.tileentity.TileEntityOnceSpawner;
import net.minecraft.block.Block;

/**
 * Created by skydark on 15-12-11.
 */
public class ModBlocks {
    public static Block onceSpawner;

    public static void init() {
        if (!Config.isOnceSpawnerDisabled) {
            onceSpawner = new OnceSpawner();
        }
    }

    public static void initTileEntity() {
        if (!Config.isOnceSpawnerDisabled) {
            GameRegistry.registerTileEntity(TileEntityOnceSpawner.class, "tile.yaum.yaum:onceSpawner");
        }
    }
}
