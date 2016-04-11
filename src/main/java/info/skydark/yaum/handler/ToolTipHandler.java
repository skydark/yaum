package info.skydark.yaum.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by skydark on 15-11-14.
 */
public class ToolTipHandler {
    private Pattern regexp;

    public ToolTipHandler(String matcher) {
        regexp = Pattern.compile(matcher);
    }

    @SubscribeEvent
    public void translateTooltip(ItemTooltipEvent event) {
        List<String> tooltips = event.toolTip;
        for (int i = 0; i < event.toolTip.size(); i++) {
            String tooltip = tooltips.get(i);
            if (tooltip == null) continue;
            Matcher m = regexp.matcher(tooltip);
            while (m.find()) {
                String translated = StatCollector.translateToLocal(m.group(1));
                tooltip = m.replaceFirst(translated);
                m = regexp.matcher(tooltip);
            }
            tooltips.set(i, tooltip);
        }
    }
}
