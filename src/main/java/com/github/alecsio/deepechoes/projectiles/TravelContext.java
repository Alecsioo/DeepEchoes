package com.github.alecsio.deepechoes.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record TravelContext(Level level, BlockPos currentPos, SonicBoomProjectile projectile) {}