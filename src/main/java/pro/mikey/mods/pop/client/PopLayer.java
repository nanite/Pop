package pro.mikey.mods.pop.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.mikey.mods.pop.client.pops.PopManager;
import pro.mikey.mods.pop.data.PopData;

import javax.annotation.Nullable;

public class PopLayer implements IGuiOverlay {
    public static final String LAYER_ID = "overlay-layer";

    private static final Logger LOGGER = LoggerFactory.getLogger(PopLayer.class);

    private PopData currentPop = null;

    public PopLayer() {
        LOGGER.info("Initializing Pop Layer");
    }

    @Nullable
    private PopData getNextOrCurrent() {
        if (currentPop == null) {
            PopManager popManager = PopManager.get();
            if (popManager.hasNext()) {
                currentPop = popManager.next();
            } else {
                currentPop = null;
            }
        }

        return currentPop;
    }

    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeigh) {
        var pop = getNextOrCurrent();
        if (pop == null) {
            return;
        }

        var renderer = pop.renderer();
        var tracker = pop.tracker();

        if (tracker.isDone()) {
            currentPop = null;
            return;
        }

        renderer.render(currentPop, guiGraphics, partialTicks);
    }
}
