package pro.mikey.mods.pop.net;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import pro.mikey.mods.pop.Pop;
import pro.mikey.mods.pop.data.Placement;
import pro.mikey.mods.pop.client.pops.PopManager;
import pro.mikey.mods.pop.client.pops.FadeInFadeOutRender;
import pro.mikey.mods.pop.client.pops.AnimTracker;
import pro.mikey.mods.pop.data.PopData;

public record ClientCreatePopPacket(
        Component content,
        Placement placement,
        int durationInSeconds
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientCreatePopPacket> TYPE = new CustomPacketPayload.Type<>(Pop.id("create_pop"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientCreatePopPacket> STREAM_CODEC = StreamCodec.composite(
            ComponentSerialization.TRUSTED_STREAM_CODEC,
            ClientCreatePopPacket::content,
            NeoForgeStreamCodecs.enumCodec(Placement.class),
            ClientCreatePopPacket::placement,
            ByteBufCodecs.INT,
            ClientCreatePopPacket::durationInSeconds,
            ClientCreatePopPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleOnClient(final ClientCreatePopPacket data, final IPayloadContext context) {
        PopManager.get().addPop(new PopData(data.content(), data.placement, new FadeInFadeOutRender(), new AnimTracker(
                data.durationInSeconds * 1000 // Convert seconds to milliseconds
        )));
    }
}
