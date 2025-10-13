package com.github.alecsio.deepechoes.registries;

import com.github.alecsio.deepechoes.DeepEchoes;
import com.github.alecsio.deepechoes.projectiles.SonicBoomProjectile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ProjectileRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, DeepEchoes.MODID);

    public static final Supplier<EntityType<SonicBoomProjectile>> SONIC_BOOM = ENTITY_TYPES.register("sonic_boom_projectile", () -> EntityType.Builder.<SonicBoomProjectile>of(SonicBoomProjectile::new, MobCategory.MISC).build("sonic_boom_projectile"));

}
