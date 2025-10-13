package com.github.alecsio.deepechoes.items;

import com.github.alecsio.deepechoes.items.factories.ProjectileFactory;
import com.github.alecsio.deepechoes.items.strategies.EchoMineAction;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EchonexPickaxe extends EchoChargedTool {

    private static final int BASE_DURABILITY = 32000;

    public EchonexPickaxe(Properties properties) {
        super((int) Math.pow(2, 16), properties.rarity(Rarity.RARE).durability(BASE_DURABILITY).component(DataComponents.TOOL, Tiers.NETHERITE.createToolProperties(BlockTags.MINEABLE_WITH_PICKAXE)), Tiers.NETHERITE);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level aLevel, Player aPlayer, InteractionHand usedHand) {
        if (aLevel.isClientSide() || !(aPlayer instanceof ServerPlayer player) || !(aLevel instanceof ServerLevel level)) {
            return super.use(aLevel, aPlayer, usedHand);
        }

        var onBlockHit = new EchoMineAction();
        var sonicBoomProjectile = ProjectileFactory.createSonicBoom(level, player, onBlockHit, null, null);
        level.addFreshEntity(sonicBoomProjectile);

        return super.use(aLevel, aPlayer, usedHand);
    }
}
