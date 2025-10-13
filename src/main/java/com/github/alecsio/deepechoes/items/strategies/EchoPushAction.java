package com.github.alecsio.deepechoes.items.strategies;

import com.github.alecsio.deepechoes.capabilities.EchoCapabilities;
import com.github.alecsio.deepechoes.capabilities.IEchoHandler;
import com.github.alecsio.deepechoes.projectiles.SonicBoomProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public class EchoPushAction implements EchoToolAction{

    private static final int PUSH_AOE_RANGE = 1;
    private static final int ECHO_PER_OPERATION = 32;

    @Override
    public void perform(EchoToolActionContext actionContext) {
        if (actionContext.level().isClientSide()) {
            return;
        }

        SonicBoomProjectile projectile = actionContext.projectile();
        BlockPos projectilePos = projectile.blockPosition();
        ServerLevel level = (ServerLevel) actionContext.level();

        AABB aabb = new AABB(projectilePos).inflate(PUSH_AOE_RANGE);
        ItemStack toolStack = actionContext.toolStack();
        IEchoHandler echoHandler = toolStack.getCapability(EchoCapabilities.ECHO_HANDLER_ITEM);
        if (echoHandler == null) {
            return;
        }

        var mobs = level.getEntitiesOfClass(Mob.class, aabb);
        for (Mob mob : mobs) {
            if (echoHandler.extractEcho(ECHO_PER_OPERATION, true) != ECHO_PER_OPERATION) {
                break;
            }
            echoHandler.extractEcho(ECHO_PER_OPERATION, false);
            mob.hurt(projectile.damageSources().thrown(projectile, projectile.getOwner()), 0f);
            mob.push(projectile.getDeltaMovement());
        }
    }
}
