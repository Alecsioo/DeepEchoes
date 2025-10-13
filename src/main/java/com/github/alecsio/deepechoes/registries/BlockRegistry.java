package com.github.alecsio.deepechoes.registries;

import com.github.alecsio.deepechoes.DeepEchoes;
import com.github.alecsio.deepechoes.blocks.CreativeEchoCharger;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DeepEchoes.MODID);
    public static final List<Supplier<? extends Block>> BLOCK_LIST = new ArrayList<>();

    public static final Supplier<Block> CREATIVE_ECHO_CHARGER = registerBlock("creative_echo_charger", CreativeEchoCharger::new);


    public static <I extends Block> Supplier<I> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends I> func) {
        return registerBlock(name, func, BlockBehaviour.Properties.of());
    }

    public static <I extends Block> Supplier<I> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends I> func, BlockBehaviour.Properties properties) {
        Supplier<I> supplier = BLOCKS.registerBlock(name, func, properties);
        BLOCK_LIST.add(supplier);
        return supplier;
    }
}
