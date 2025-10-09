package com.github.alecsio.deepechoes.datagenerators;

import com.github.alecsio.deepechoes.DeepEchoes;
import com.github.alecsio.deepechoes.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = DeepEchoes.MODID)
public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DeepEchoes.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ItemRegistry.RAW_ECHONEX);
        basicItem(ItemRegistry.RAW_CORRUPTED_ECHONEX);
        basicItem(ItemRegistry.ECHONEX_INGOT);
        basicItem(ItemRegistry.CORRUPTED_ECHONEX_INGOT);
    }

    private void basicItem(Supplier<Item> itemSupplier) {
        basicItem(itemSupplier.get());
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        LOGGER.debug("Handling gather data event");

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ItemModelGenerator(output, existingFileHelper));
    }
}
