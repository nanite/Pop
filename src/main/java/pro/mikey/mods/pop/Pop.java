package pro.mikey.mods.pop;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import pro.mikey.mods.pop.client.ClientInit;
import pro.mikey.mods.pop.net.Networking;

@Mod(Pop.MODID)
public class Pop {
    public static final String MODID = "pop";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Pop(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(Networking::register);

        NeoForge.EVENT_BUS.register(this);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ClientInit::onScreenRender);
        }

//        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        ClientInit.init();
    }

    @SubscribeEvent
    private void onRegisterCommands(final RegisterCommandsEvent event) {
        event.getDispatcher().register(PopCommands.register(event.getBuildContext()));
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
