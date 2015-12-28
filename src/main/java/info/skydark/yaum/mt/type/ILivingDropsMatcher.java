package info.skydark.yaum.mt.type;

import info.skydark.yaum.mt.event.EventWrapper;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * Created by skydark on 15-11-16.
 */
@ZenClass("mod.yaum.Drops.ILivingDropsMatcher")
public interface ILivingDropsMatcher {
    boolean match(EventWrapper.MyLivingDropsEvent ev);
}