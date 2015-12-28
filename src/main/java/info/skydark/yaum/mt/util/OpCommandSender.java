package info.skydark.yaum.mt.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

/**
 * Created by skydark on 15-11-20.
 */
public class OpCommandSender implements ICommandSender {
    private final String name;
    private final ChunkCoordinates coord;
    private final World world;

    public OpCommandSender(EntityPlayer player) {
        name = player.getCommandSenderName();
        coord = player.getPlayerCoordinates();
        world = player.getEntityWorld();
    }

    public OpCommandSender(World world, int x, int y, int z, String name) {
        this.name = name;
        this.coord = new ChunkCoordinates(x, y, z);
        this.world = world;
    }

    @Override
    public String getCommandSenderName() {
        return name;
    }

    @Override
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_) {}

    @Override
    public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
        return p_70003_1_ <= 2;
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return coord;
    }

    @Override
    public World getEntityWorld() {
        return world;
    }
}
