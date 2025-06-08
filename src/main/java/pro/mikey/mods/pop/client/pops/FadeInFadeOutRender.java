package pro.mikey.mods.pop.client.pops;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import pro.mikey.mods.pop.data.AnimStage;
import pro.mikey.mods.pop.data.PopData;

/**
 * Simple pop that will wiggle whilst being rendered.
 */
public class FadeInFadeOutRender implements IPopRender {
    float lastOpacity = 0.0F;

    @Override
    public void render(PopData pop, GuiGraphics graphics, float partialTicks) {
        pop.tracker().onRenderFrame();
        var stage = pop.tracker().getStage();
        var currentStageCompletion = pop.tracker().currentStageCompletion();

        // Fade in and out, smoothly by lerping the opacity
        float opacity = 1.0f;
        float xOffset = 0.0f;
        if (stage != AnimStage.IDLE) {
            float nextOpacity = stage == AnimStage.IN ? currentStageCompletion / 100.0F : 1.0F - (currentStageCompletion / 100.0F);
            lastOpacity = nextOpacity;
            opacity = Mth.lerp(partialTicks, lastOpacity, nextOpacity);
        }

        PoseStack pose = graphics.pose();
        pose.pushPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);

        // Draw the text
        var textWidth = Minecraft.getInstance().font.width(pop.content());
        var screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        var screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        var location = pop.placement().location(screenWidth, screenHeight, textWidth);
        int x = location[0];
        int y = location[1];

        graphics.drawString(Minecraft.getInstance().font, pop.content(), x, (int) (y + xOffset), 0xFFFFFF);
        pose.popPose();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

// Just for debugging
//        pose.popPose();
//
//        var debugX = 10;
//        var debugY = screenHeight - 50;
//
//        pose.pushPose();
//        int color = 0xFF00FF;
//        graphics.drawString(Minecraft.getInstance().font, "Stage: " + stage, debugX, debugY + 10, color);
//        graphics.drawString(Minecraft.getInstance().font, "Duration: " + durationInTicks, debugX, debugY + 20, color);
//        graphics.drawString(Minecraft.getInstance().font, "Time: " + gameTime, debugX, debugY + 30, color);
//        graphics.drawString(Minecraft.getInstance().font, "Total Duration: " + pop.tracker().totalDuration(), debugX, debugY + 40, color);
//        pose.popPose();
    }
}
