package info.skydark.yaum;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.skydark.yaum.dispenser.DispenserAddons;
import info.skydark.yaum.handler.ToolTipHandler;
import info.skydark.yaum.mt.MineTweakerIntegration;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;


@Mod(modid = Yaum.MODID, version = Yaum.VERSION, dependencies = "after:MineTweaker3")
public class Yaum
{
    public static final String MODID = "${MOD_ID}";
    public static final String VERSION = "${MOD_VERSION}";

    public static boolean mtIntegration = false;

    public static CreativeTabs yaumTab = new CreativeTabs("yaum") {

        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem(){
            return Items.shears;
        }
    };

    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        Config.init(event.getSuggestedConfigurationFile());
        mtIntegration = Config.mtIntegration && Loader.isModLoaded("MineTweaker3");

        ModItems.init();
        ModBlocks.init();
        if (mtIntegration) {
            MineTweakerIntegration.init();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Recipes.init();
        ModBlocks.initTileEntity();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (!Config.tooltipMatcher.isEmpty()) {
            MinecraftForge.EVENT_BUS.register(new ToolTipHandler(Config.tooltipMatcher));
        }
        if (Config.dispenserFillCauldron) {
            DispenserAddons.init();
        }
    }
}
