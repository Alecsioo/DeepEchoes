package com.github.alecsio.deepechoes.items;

import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;

public class EchoChargedTool extends EchoChargedItem {

    public EchoChargedTool(int maxEcho, Properties properties, Tier tier) {
        super(maxEcho, properties.attributes(DiggerItem.createAttributes(tier, 1.0f, 1.0f)), tier);
    }
}
