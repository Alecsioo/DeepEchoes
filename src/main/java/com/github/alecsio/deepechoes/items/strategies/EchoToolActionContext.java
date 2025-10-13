package com.github.alecsio.deepechoes.items.strategies;

import com.github.alecsio.deepechoes.projectiles.SonicBoomProjectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public record EchoToolActionContext(Level level, Player player, SonicBoomProjectile projectile, ItemStack toolStack) {}
