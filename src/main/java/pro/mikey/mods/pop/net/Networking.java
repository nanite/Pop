package pro.mikey.mods.pop.net;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import pro.mikey.mods.pop.Pop;

public class Networking {
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Pop.MODID, "main_network_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        HANDLER.registerMessage(0, ClientCreatePopPacket.class, ClientCreatePopPacket::encode, ClientCreatePopPacket::decode, ClientCreatePopPacket::handle);
    }

    public static void sendTo(Object msg, ServerPlayer player) {
        if (!(player instanceof FakePlayer))
            HANDLER.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToAll(Object msg, Level level) {
        for (Player player : level.players()) {
            if (!(player instanceof FakePlayer))
                HANDLER.sendTo(msg, ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}
