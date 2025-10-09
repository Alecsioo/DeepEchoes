package com.github.alecsio.deepechoes.registries;

import com.github.alecsio.deepechoes.DeepEchoes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DeepEchoes.MODID);
    // Useful to have for scenarios where a list of all registered items for this mod is needed, ie creative tab
    public static final List<Supplier<? extends Item>> ITEM_LIST = new ArrayList<>();

    public static final Supplier<Item> ECHONEX_INGOT = registerItem("echonex_ingot", Item::new);
    public static final Supplier<Item> CORRUPTED_ECHONEX_INGOT = registerItem("corrupted_echonex_ingot", Item::new);
    public static final Supplier<Item> RAW_ECHONEX = registerItem("raw_echonex", Item::new);
    public static final Supplier<Item> RAW_CORRUPTED_ECHONEX = registerItem("raw_corrupted_echonex", Item::new);


    public static <I extends Item> Supplier<I> registerItem(String name, Function<Item.Properties, ? extends I> func) {
        return registerItem(name, func, new Item.Properties());
    }

    public static <I extends Item> Supplier<I> registerItem(String name, Function<Item.Properties, ? extends I> func, Item.Properties properties) {
        Supplier<I> supplier = ITEMS.registerItem(name, func, properties);
        ITEM_LIST.add(supplier);
        return supplier;
    }

}
