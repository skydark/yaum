package info.skydark.yaum.util;

import info.skydark.yaum.mt.util.OpCommandSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.IAdminCommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * Created by skydark on 15-12-11.
 */
public class CommandHelper {
    public static void executeCommand(ICommandSender sender, String command) {
        executeCommand(sender, command, true);
    }

    public static void executeCommandAsOp(EntityPlayer player, String command) {
        executeCommand(new OpCommandSender(player), command, true);
    }

    public static void executeCommandAsOp(World world, int x, int y, int z, String name, String command) {
        executeCommand(new OpCommandSender(world, x, y, z, name), command, true);
    }

    public static void executeCommand(ICommandSender sender, String command, boolean nolog) {
        if (sender.getEntityWorld().isRemote) {
            return;
        }
        MinecraftServer server = MinecraftServer.getServer();
        if (server != null) {
            ICommandManager commandManager = server.getCommandManager();
            if (commandManager != null) {
                // safer to use reflection
                if (nolog && (commandManager instanceof IAdminCommand)) {
                    IAdminCommand serverCommandManager = (IAdminCommand) commandManager;
                    CommandBase.setAdminCommander(null);
                    commandManager.executeCommand(sender, command);
                    CommandBase.setAdminCommander(serverCommandManager);
                } else {
                    commandManager.executeCommand(sender, command);
                }
            }
        }
    }
}
