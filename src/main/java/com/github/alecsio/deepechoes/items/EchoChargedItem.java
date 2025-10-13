package com.github.alecsio.deepechoes.items;

import com.github.alecsio.deepechoes.registries.DataComponentRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class EchoChargedItem extends TieredItem {

    public record EchoCharge(int storedEcho, int maxEcho) {}

    public static final Codec<EchoCharge> ECHO_CHARGE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("storedEcho").forGetter(EchoCharge::storedEcho),
            Codec.INT.fieldOf("maxEcho").forGetter(EchoCharge::maxEcho)
    ).apply(instance, EchoCharge::new));

    public static final StreamCodec<ByteBuf, EchoCharge> ECHO_CHARGE_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, EchoCharge::storedEcho,
            ByteBufCodecs.INT, EchoCharge::maxEcho,
            EchoCharge::new
    );

    private static final EchoTooltipProvider TOOLTIP_PROVIDER = EchoTooltipProvider.getInstance();

    public EchoChargedItem(int maxEcho, Properties properties, Tier tier) {
        super(tier, properties.component(DataComponentRegistry.ECHO_CHARGE.get(), new EchoCharge(0, maxEcho)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        Component component = TOOLTIP_PROVIDER.getTooltipComponentFor(stack);
        tooltipComponents.add(component);
    }
}
