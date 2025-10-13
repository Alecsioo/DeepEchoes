package com.github.alecsio.deepechoes.items.strategies;

import com.github.alecsio.deepechoes.capabilities.EchoCapabilities;
import com.github.alecsio.deepechoes.capabilities.IEchoHandler;
import com.github.alecsio.deepechoes.projectiles.SonicBoomProjectile;
import com.github.alecsio.deepechoes.utilities.BlockUtilities;
import com.github.alecsio.deepechoes.utilities.SoundUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Random;

public class EchoMineAction implements EchoToolAction {

    private static final int MAX_BLOCK_HITS = 5;
    private static final int CONSUMED_ECHO_PER_OPERATION = 32;
    private static final int RANGE = 1;
    private static final Random RANDOM = new Random();

    @Override
    public void perform(EchoToolActionContext actionContext) {
        if (actionContext.level().isClientSide()) {
            return;
        }

        SonicBoomProjectile projectile = actionContext.projectile();
        if (projectile.getBlockHits() >= MAX_BLOCK_HITS) {
            projectile.discard();
            return;
        }


        ItemStack toolStack = actionContext.toolStack();
        IEchoHandler echoHandler = toolStack.getCapability(EchoCapabilities.ECHO_HANDLER_ITEM);
        if (echoHandler == null) {
            return;
        }

        ServerLevel level = (ServerLevel) actionContext.level();
        List<BlockPos> toDestroy = BlockUtilities.getBlocksInRange(RANGE, projectile);
        if (toDestroy.isEmpty()) {
            return;
        }

        for (BlockPos pos : toDestroy) {
            var blockState = level.getBlockState(pos);
            if (canBreak(blockState, toolStack)) {
                if (echoHandler.extractEcho(CONSUMED_ECHO_PER_OPERATION, true) < CONSUMED_ECHO_PER_OPERATION) {
                    ServerPlayer player = (ServerPlayer) actionContext.player();
                    SoundUtilities.playEchoDepleted(level, player, RANDOM);
                    break;
                }

                echoHandler.extractEcho(CONSUMED_ECHO_PER_OPERATION, false);
                toolStack.setDamageValue(toolStack.getDamageValue() + 1);
                level.destroyBlock(pos, true);
            }
        }
    }

    private boolean canBreak(BlockState toBreak, ItemStack toolStack) {
        return !toBreak.isAir() && toolStack.isCorrectToolForDrops(toBreak);
    }
}
