package pro.mikey.mods.pop.client;

import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientInit {
    public static void init() {

    }

    @SubscribeEvent
    public static void onScreenRender(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(PopLayer.LAYER_ID, new PopLayer());
    }
}
