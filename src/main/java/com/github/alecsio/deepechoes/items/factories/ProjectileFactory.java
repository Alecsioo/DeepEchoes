package com.github.alecsio.deepechoes.items.factories;

import com.github.alecsio.deepechoes.items.strategies.EchoToolAction;
import com.github.alecsio.deepechoes.projectiles.SonicBoomProjectile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class ProjectileFactory {

    private ProjectileFactory() {}

    public static SonicBoomProjectile createSonicBoom(ServerLevel level, ServerPlayer player, EchoToolAction onBlockHit, EchoToolAction onEntityHit, EchoToolAction onTick) {
        SonicBoomProjectile proj = new SonicBoomProjectile(level, player, onBlockHit, onEntityHit, onTick);
        Vec3 eye = player.getEyePosition(1.0F);
        proj.setPos(eye.x, eye.y, eye.z);
        proj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.75F, 0.0F);
        return proj;
    }

}
