package com.github.alecsio.deepechoes.utilities;

import com.github.alecsio.deepechoes.projectiles.SonicBoomProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class BlockUtilities {

    // Adapted from https://github.com/Tutorials-By-Kaupenjoe/NeoForge-Tutorial-1.21.X/blob/main/src/main/java/net/kaupenjoe/tutorialmod/item/custom/HammerItem.java
    public static List<BlockPos> getBlocksInRange(int range, SonicBoomProjectile projectile) {
        List<BlockPos> positions = new ArrayList<>();
        Vec3 initialBlockPos = projectile.position();
        BlockPos pos = BlockPos.containing(initialBlockPos.x, initialBlockPos.y, initialBlockPos.z);

        BlockHitResult traceResult = projectile.level().clip(new ClipContext(projectile.getPosition(1f),
                (projectile.getPosition(1.0f).add(projectile.getDeltaMovement().scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, projectile));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if(traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
            pos = pos.relative(traceResult.getDirection(), -1);
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + y));
                }
            }
        }

        if(traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
            pos = pos.relative(traceResult.getDirection(), -1);
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ()));
                }
            }
        }

        if(traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            pos = pos.relative(traceResult.getDirection(), -1);
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(pos.getX(), pos.getY() + y, pos.getZ() + x));
                }
            }
        }

        return positions;
    }
}
