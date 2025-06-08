package pro.mikey.mods.pop.client.pops;

import net.minecraft.client.gui.GuiGraphics;
import pro.mikey.mods.pop.data.PopData;

public interface IPopRender {
    void render(PopData popData, GuiGraphics graphics, float partialTicks);
}
