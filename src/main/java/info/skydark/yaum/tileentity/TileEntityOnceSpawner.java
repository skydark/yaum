package info.skydark.yaum.tileentity;

import cpw.mods.fml.common.FMLLog;
import info.skydark.yaum.mt.util.OpCommandSender;
import info.skydark.yaum.util.CommandHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skydark on 15-12-11.
 */
public class TileEntityOnceSpawner extends TileEntity {
    public static final int RANGE = 10;
    // public String condition = "";
    public int range = RANGE;
    public int rangey = RANGE;
    public String action = "";
    public int interval = 20;
    private int counter = 0;

    public void readFromNBT(NBTTagCompound p_145839_1_) {
        readFromItemNBT(p_145839_1_);
        super.readFromNBT(p_145839_1_);
    }

    public void readFromItemNBT(NBTTagCompound p_145839_1_) {
        // condition = p_145839_1_.getString("condition");
        action = p_145839_1_.getString("action");
        interval = p_145839_1_.getInteger("interval");
        interval = interval > 0 ? interval : 20;
        range = p_145839_1_.getInteger("range");
        range = range > 0 ? range : RANGE;
        rangey = p_145839_1_.getInteger("rangey");
        rangey = rangey > 0 ? rangey : range;
        counter = p_145839_1_.getInteger("counter");
    }

    public void writeToNBT(NBTTagCompound p_145841_1_) {
        // p_145841_1_.setString("condition", condition);
        p_145841_1_.setString("action", action);
        p_145841_1_.setInteger("interval", interval);
        p_145841_1_.setInteger("range", range);
        p_145841_1_.setInteger("rangey", rangey);
        p_145841_1_.setInteger("counter", counter);
        super.writeToNBT(p_145841_1_);
    }

    private boolean checkCondition(EntityPlayer player) {
        if (Math.abs(player.posX - xCoord) <= range && Math.abs(player.posZ - zCoord) <= range
                && Math.abs(player.posY - yCoord) <= rangey) {
            if (!player.capabilities.isCreativeMode) {
                return true;
            }
        }
        return false;
    }

    /*
     * return true if action success
     */
    private boolean doAction(EntityPlayer player) {
        try {
            if (action.startsWith("@")) {
                CommandHelper.executeCommandAsOp(player, action.substring(1));
            } else {
                CommandHelper.executeCommandAsOp(worldObj, xCoord, yCoord, zCoord, "", action);
            }
            return true;
        } catch (Throwable t) {
            FMLLog.warning("Error occurred while executing command:" + action);
        }
        return false;
    }

    public void updateEntity() {
        counter++;
        if (counter < interval) {
            return;
        }
        counter = 0;

        List<EntityPlayer> players = new ArrayList<EntityPlayer>(worldObj.playerEntities);
        for (EntityPlayer player : players) {
            if (checkCondition(player)) {
                if (doAction(player)) {
                    worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 2);
                    return;
                }
            }
        }
    }

    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
    }
}
