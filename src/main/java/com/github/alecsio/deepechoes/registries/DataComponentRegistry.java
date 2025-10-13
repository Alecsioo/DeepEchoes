package com.github.alecsio.deepechoes.registries;

import com.github.alecsio.deepechoes.DeepEchoes;
import com.github.alecsio.deepechoes.items.EchoChargedItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataComponentRegistry {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, DeepEchoes.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<EchoChargedItem.EchoCharge>> ECHO_CHARGE = DATA_COMPONENTS.registerComponentType(
            "echo_charge",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(EchoChargedItem.ECHO_CHARGE_CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(EchoChargedItem.ECHO_CHARGE_STREAM_CODEC)
    );
}
