package pro.mikey.mods.pop.client;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class ClientInit {
    public static void init() {

    }

    @SubscribeEvent
    public static void onScreenRender(RegisterGuiLayersEvent event) {
        event.registerAboveAll(PopLayer.LAYER_ID, new PopLayer());
    }
}
