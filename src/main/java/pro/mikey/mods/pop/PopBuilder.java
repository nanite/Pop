package pro.mikey.mods.pop;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import pro.mikey.mods.pop.client.pops.AnimTracker;
import pro.mikey.mods.pop.client.pops.FadeInFadeOutRender;
import pro.mikey.mods.pop.data.Placement;
import pro.mikey.mods.pop.client.pops.PopManager;
import pro.mikey.mods.pop.data.PopData;
import pro.mikey.mods.pop.net.ClientCreatePopPacket;

/**
 * This class is almost exclusively added for KubeJS support.
 */
@SuppressWarnings("unused")
public class PopBuilder {
    private static final Component DEFAULT_CONTENT = Component.literal("Hello from PopBuilder!");

    private int durationInSeconds = 5;
    private Component content = DEFAULT_CONTENT;
    private Placement placement = Placement.MIDDLE_CENTER;

    private PopBuilder() {
        // Private constructor to prevent instantiation
    }

    public static PopBuilder create() {
        return new PopBuilder();
    }

    public PopBuilder duration(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
        return this;
    }

    public PopBuilder content(Component content) {
        this.content = content;
        return this;
    }

    public PopBuilder placement(Placement placement) {
        this.placement = placement;
        return this;
    }

    public PopBuilder placement(String placement) {
        return placementByString(placement);
    }

    public PopBuilder placementByString(String placement) {
        this.placement = Placement.fromString(placement);
        return this;
    }

    public void sendToPlayer(Player player) {
        if (player.level().isClientSide()) {
            throw new IllegalStateException("This method should only be called on the server side");
        }

        var packet = new ClientCreatePopPacket(content, placement, durationInSeconds);

        // Send the packet to the player
        PacketDistributor.sendToPlayer((ServerPlayer) player, packet);
    }

    public void display() {
        PopManager.get().addPop(this.build());
    }

    public PopData build() {
        return new PopData(content, placement, new FadeInFadeOutRender(), new AnimTracker(
                durationInSeconds * 1000 // Convert seconds to milliseconds
        ));
    }
}
