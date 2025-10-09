package com.github.alecsio.deepechoes.capabilities;

import com.github.alecsio.deepechoes.DeepEchoes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = DeepEchoes.MODID)
public class EchoCapabilities {

    private static final ResourceLocation ECHO_CAPABILITY_RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(DeepEchoes.MODID, "echo_handler");

    public static final BlockCapability<IEchoHandler, Void> ECHO_HANDLER_BLOCK = BlockCapability.createVoid(
            ECHO_CAPABILITY_RESOURCE_LOCATION,
            IEchoHandler.class
    );

    public static final ItemCapability<IEchoHandler, Void> ECHO_HANDLER_ITEM = ItemCapability.createVoid(
            ECHO_CAPABILITY_RESOURCE_LOCATION,
            IEchoHandler.class
    );

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {

    }

}
