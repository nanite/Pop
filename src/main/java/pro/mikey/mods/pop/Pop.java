package pro.mikey.mods.pop;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import pro.mikey.mods.pop.client.ClientInit;
import pro.mikey.mods.pop.net.Networking;

@Mod(Pop.MODID)
public class Pop {
    public static final String MODID = "pop";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Pop() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ClientInit::onScreenRender);
        }

        Networking.register();
    }


    private void onClientSetup(final FMLClientSetupEvent event) {
        ClientInit.init();
    }

    @SubscribeEvent
    public void onRegisterCommands(final RegisterCommandsEvent event) {
        event.getDispatcher().register(PopCommands.register(event.getBuildContext()));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
