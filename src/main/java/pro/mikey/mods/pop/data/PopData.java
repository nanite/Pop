package pro.mikey.mods.pop.data;

import net.minecraft.network.chat.Component;
import pro.mikey.mods.pop.client.pops.AnimTracker;
import pro.mikey.mods.pop.client.pops.IPopRender;

public record PopData(
        Component content,
        Placement placement,
        IPopRender renderer,
        AnimTracker tracker
) {
}
