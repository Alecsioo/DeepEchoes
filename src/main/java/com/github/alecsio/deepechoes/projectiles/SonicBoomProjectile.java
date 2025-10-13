package com.github.alecsio.deepechoes.projectiles;

import com.github.alecsio.deepechoes.items.strategies.EchoToolAction;
import com.github.alecsio.deepechoes.items.strategies.EchoToolActionContext;
import com.github.alecsio.deepechoes.registries.ProjectileRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SonicBoomProjectile extends ThrowableProjectile {

    private EchoToolAction onEntityHit;
    private EchoToolAction onBlockHit;
    private int blockHits = 0;
    private EchoToolAction onTick;

    public SonicBoomProjectile(EntityType<SonicBoomProjectile> sonicBoomProjectileEntityType, Level level) {
        super(sonicBoomProjectileEntityType, level);
        noPhysics = true;
    }

    public SonicBoomProjectile(ServerLevel level, ServerPlayer player, EchoToolAction onBlockHit, EchoToolAction onEntityHit, EchoToolAction onTick) {
        super(ProjectileRegistry.SONIC_BOOM.get(), player, level);
        this.onBlockHit = onBlockHit;
        this.onEntityHit = onEntityHit;
        this.onTick = onTick;
    }

    public SonicBoomProjectile(Level level, EchoToolAction onEntityHit, EchoToolAction onBlockHit, EchoToolAction onTick) {
        super(ProjectileRegistry.SONIC_BOOM.get(), level);
        this.onEntityHit = onEntityHit;
        this.onBlockHit = onBlockHit;
        this.onTick = onTick;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (level().isClientSide()) {
            return;
        }

        var actionContext = buildEchoToolActionContext();
        if (onEntityHit != null) {
            onEntityHit.perform(actionContext);
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (level().isClientSide()) {
            return;
        }

        var actionContext = buildEchoToolActionContext();
        if (onBlockHit != null) {
            onBlockHit.perform(actionContext);
        }
        blockHits++;
        super.onHitBlock(result);
    }

    public int getBlockHits() {
        return blockHits;
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
            return;
        }

        var actionContext = buildEchoToolActionContext();
        if (onTick != null) {
            onTick.perform(actionContext);
        }
        if (tickCount >= 20 * 5) {
            this.discard();
        }
        double d0 = this.getGravity();
        super.tick();
        // Extending ThrowableProjectile is useful for reusing most of its tick logic, but in this case applying gravity
        // is not a desired behaviour, therefore I'm basically reverting the gravity changes that happen inside super.tick()
        offsetGravity(d0);
    }

    private void offsetGravity(double d0) {
        if (d0 != 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, d0, 0.0));
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    private EchoToolActionContext buildEchoToolActionContext() {
        ServerPlayer sPlayer = ((ServerPlayer) this.getOwner());
        ItemStack hStack = sPlayer.getMainHandItem();
        return new EchoToolActionContext(level(), sPlayer, this, hStack);
    }
}
