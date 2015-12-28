package info.skydark.yaum.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.util.TupleIntJsonSerializable;

import java.util.Map;

/**
 * Created by skydark on 15-11-13.
 */
public class NBTHelper {
    public static NBTTagCompound getTagCompound(ItemStack stack) {
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }
        return nbtTagCompound;
    }

    public static NBTBase getTagByPath(ItemStack stack, String path) {
        NBTTagCompound nbtTagCompound = getTagCompound(stack);
        NBTBase result = nbtTagCompound;
        for (String entry : path.split("/")) {
            if (result instanceof NBTTagCompound) {
                result = ((NBTTagCompound) result).getTag(entry);
            } else {
                return null;
            }
        }
        return result;
    }

    public static boolean setTagByPath(ItemStack stack, String path, NBTBase tag) {
        if (stack == null || tag == null || path.isEmpty()) return false;
        NBTTagCompound nbtTagCompound = getTagCompound(stack);
        NBTTagCompound parent = nbtTagCompound;
        String[] entries = path.split("/");
        for (int i = 0; i < entries.length - 1; i++) {
            String entry = entries[i];
            NBTBase nbtTag = parent.getTag(entry);
            if (nbtTag == null) {
                parent.setTag(entry, new NBTTagCompound());
            } else if (!(nbtTag instanceof NBTTagCompound)) {
                return false;
            }
            parent = parent.getCompoundTag(entry);
        }
        parent.setTag(entries[entries.length-1], tag);
        stack.setTagCompound(nbtTagCompound);
        return true;
    }

    public static void setTag(ItemStack stack, String name, String value) {
        NBTTagCompound nbtTagCompound = getTagCompound(stack);
        nbtTagCompound.setString(name, value);
        stack.setTagCompound(nbtTagCompound);
    }

    public static void setTag(ItemStack stack, String name, int value) {
        NBTTagCompound nbtTagCompound = getTagCompound(stack);
        nbtTagCompound.setInteger(name, value);
        stack.setTagCompound(nbtTagCompound);
    }

    public static void setTag(ItemStack stack, String name, NBTTagCompound value) {
        NBTTagCompound nbtTagCompound = getTagCompound(stack);
        nbtTagCompound.setTag(name, value);
        stack.setTagCompound(nbtTagCompound);
    }

    public static NBTTagCompound fromStatMap(Map<StatBase, TupleIntJsonSerializable> map) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        for (Map.Entry<StatBase, TupleIntJsonSerializable> entry : map.entrySet()) {
            if (entry.getValue().getJsonSerializableValue() == null) {
                nbtTagCompound.setInteger(entry.getKey().statId.replace('.', '_'), entry.getValue().getIntegerValue());
            }
        }
        return nbtTagCompound;
    }
}
