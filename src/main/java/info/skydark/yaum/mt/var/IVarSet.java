package info.skydark.yaum.mt.var;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMemberGetter;
import stanhebben.zenscript.annotations.ZenMemberSetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by skydark on 15-12-17.
 */
@ZenClass("mod.yaum.type.IVarSet")
public interface IVarSet {
    // @ZenMemberSetter
    @ZenMethod("set")
    public void setVar(String name, int value);

    @ZenMethod("get")
    @ZenMemberGetter
    public int getVar(String name);
}
