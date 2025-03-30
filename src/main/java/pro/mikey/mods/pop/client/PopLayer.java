package pro.mikey.mods.pop.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.mikey.mods.pop.Pop;
import pro.mikey.mods.pop.client.pops.PopManager;
import pro.mikey.mods.pop.data.PopData;

import javax.annotation.Nullable;

public class PopLayer implements LayeredDraw.Layer {
    public static final ResourceLocation LAYER_ID = Pop.id("pop_layer");

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
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
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

        renderer.render(currentPop, guiGraphics, deltaTracker);
    }
}
