package pro.mikey.mods.pop.net;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class Networking {
    private static final String VERSION = "1";

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(VERSION);

        registrar.playToClient(ClientCreatePopPacket.TYPE, ClientCreatePopPacket.STREAM_CODEC, ClientCreatePopPacket::handleOnClient);
    }
}
