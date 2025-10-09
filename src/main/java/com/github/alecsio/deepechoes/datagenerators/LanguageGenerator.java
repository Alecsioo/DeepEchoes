package com.github.alecsio.deepechoes.datagenerators;

import com.github.alecsio.deepechoes.DeepEchoes;
import com.github.alecsio.deepechoes.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = DeepEchoes.MODID)
public class LanguageGenerator extends LanguageProvider {

    public LanguageGenerator(PackOutput output) {
        super(output, DeepEchoes.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItemTranslations();
    }

    private void addItemTranslations() {
        addItem(ItemRegistry.RAW_ECHONEX, "Raw Echonex");
        addItem(ItemRegistry.ECHONEX_INGOT, "Echonex Ingot");
        addItem(ItemRegistry.RAW_CORRUPTED_ECHONEX, "Raw Corrupted Echonex");
        addItem(ItemRegistry.CORRUPTED_ECHONEX_INGOT, "Corrupted Echonex Ingot");
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        LOGGER.debug("Handling gather data event");

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event.includeClient(), new LanguageGenerator(output));
    }

}
