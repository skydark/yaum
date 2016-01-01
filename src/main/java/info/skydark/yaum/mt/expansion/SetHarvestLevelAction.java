package info.skydark.yaum.mt.expansion;

import minetweaker.IUndoableAction;
import net.minecraft.item.ItemStack;

/**
 * Created by skydark on 16-1-2.
 */
public class SetHarvestLevelAction implements IUndoableAction {
    private final ItemStack stack;
    private final String toolClass;
    private final int oldValue;
    private final int value;

    public SetHarvestLevelAction(ItemStack stack, String toolClass, int value) {
        this.stack = stack;
        this.toolClass = toolClass;
        this.oldValue = stack.getItem().getHarvestLevel(stack, toolClass);
        this.value = value;
    }

    /**
     * Executes what the action is supposed to do. This method can be called
     * again if undo() has been called in between.
     */
    @Override
    public void apply() {
        stack.getItem().setHarvestLevel(toolClass, value);
    }

    /**
     * Checks if this action can be undone. If this method returns true, it must
     * implement undo() properly. If this method returns false, the action is
     * considered permanent, undo() will never be called, and the action cannot
     * be executed as part of a server script. Additionally, the
     * minetweaker.clear() function will not work and minetweaker.canClear will
     * return false.
     *
     * @return undoable status
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Undoes the action. Will only be called after apply() has been executed.
     * After an undo, apply may be called again. They could be done multiple
     * times in certain scenarios.
     */
    @Override
    public void undo() {
        stack.getItem().setHarvestLevel(toolClass, oldValue);
    }

    /**
     * Describes, in a single human-readable sentence, what this specific action
     * is doing. Used in logging messages, lists, ...
     * <p/>
     * Try to be as descriptive as possible without being too verbose.
     * <p/>
     * Examples: - Adding Peach planks to the woodPlanks ore dictionary entry -
     * Removing a recipe for Iron Ore
     *
     * @return the description of this action
     */
    @Override
    public String describe() {
        return "Setting harvesting level of  " + stack.getDisplayName() + " to " + value;
    }

    /**
     * Describes what this action does if it is undone. Similar to the
     * describe() method. No implementation is needed if the action cannot be
     * undone.
     *
     * @return the description of this action, when undone
     */
    @Override
    public String describeUndo() {
        return "Reverting harvesting level of " + stack.getDisplayName() + " to " + oldValue;
    }

    /**
     * Returns the override key. Two actions are considered to override each
     * other if their override key is equal. You can return null to indicate
     * that an action can never be overridden.
     * <p/>
     * This value only makes sense for recipes that are not undoable. For
     * undoable recipes, you should return null.
     *
     * @return override key (null for actions that are undoable or which can
     * never be overridden by another action)
     */
    @Override
    public Object getOverrideKey() {
        return null;
    }
}
