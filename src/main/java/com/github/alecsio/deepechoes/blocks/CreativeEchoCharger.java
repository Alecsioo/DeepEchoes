package com.github.alecsio.deepechoes.blocks;

import com.github.alecsio.deepechoes.capabilities.EchoCapabilities;
import com.github.alecsio.deepechoes.capabilities.IEchoHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CreativeEchoCharger extends Block {

    public CreativeEchoCharger(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        IEchoHandler echoHandler = stack.getCapability(EchoCapabilities.ECHO_HANDLER_ITEM);
        if (echoHandler != null) {
            echoHandler.insertEcho(echoHandler.getMaxEcho(), false);
            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
