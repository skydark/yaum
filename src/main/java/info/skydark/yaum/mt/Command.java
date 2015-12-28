package info.skydark.yaum.mt;

import info.skydark.yaum.mt.util.OpCommandSender;
import info.skydark.yaum.util.CommandHelper;
import minetweaker.MineTweakerAPI;
import minetweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-11-20.
 */
@ZenClass("mod.yaum.Command")
public class Command {
    @ZenMethod
    public static void execute(IPlayer iPlayer, String command) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player != null) {
            try {
                CommandHelper.executeCommand(player, command);
            } catch (Throwable t) {
                MineTweakerAPI.logError("Error occurred while executing command:" + command);
            }
        }
    }

    @ZenMethod
    public static void executeAsOp(String command) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server != null) {
            try {
                CommandHelper.executeCommand(server, command);
            } catch (Throwable t) {
                MineTweakerAPI.logError("Error occurred while executing command:" + command);
            }
        }
    }

    @ZenMethod
    public static void executeAsOp(IPlayer iPlayer, String command) {
        EntityPlayer player = MTHelper.getPlayer(iPlayer);
        if (player != null) {
            try {
                CommandHelper.executeCommandAsOp(player, command);
            } catch (Throwable t) {
                MineTweakerAPI.logError("Error occurred while executing command:" + command);
            }
        }
    }
}
