package pro.mikey.mods.pop.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.mikey.mods.pop.data.Placement;
import pro.mikey.mods.pop.client.pops.PopManager;
import pro.mikey.mods.pop.client.pops.FadeInFadeOutRender;
import pro.mikey.mods.pop.client.pops.AnimTracker;
import pro.mikey.mods.pop.data.PopData;

import java.util.function.Supplier;

public class ClientCreatePopPacket {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCreatePopPacket.class);

    Component content;
    Placement placement;
    int durationInSeconds;

    public ClientCreatePopPacket(Component content, Placement placement, int durationInSeconds) {
        this.content = content;
        this.placement = placement;
        this.durationInSeconds = durationInSeconds;
    }

    public static ClientCreatePopPacket decode(FriendlyByteBuf buf) {
        return new ClientCreatePopPacket(
                buf.readComponent(),
                Placement.fromString(buf.readUtf()),
                buf.readInt()
        );
    }

    public static void encode(ClientCreatePopPacket message, FriendlyByteBuf buf) {
        buf.writeComponent(message.content);
        buf.writeUtf(message.placement.toString());
        buf.writeInt(message.durationInSeconds);
    }

    public static void handle(ClientCreatePopPacket message, Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection() != NetworkDirection.PLAY_TO_CLIENT) {
            LOGGER.warn("Received ClientCreatePopPacket on the wrong side: {}", context.get().getDirection());
            return;
        }

        context.get().enqueueWork(() -> {
            PopManager.get().addPop(new PopData(message.content, message.placement, new FadeInFadeOutRender(), new AnimTracker(
                    message.durationInSeconds * 1000 // Convert seconds to milliseconds
            )));
        });

        context.get().setPacketHandled(true);
    }
}
