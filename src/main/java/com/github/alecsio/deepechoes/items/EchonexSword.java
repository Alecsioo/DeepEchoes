package com.github.alecsio.deepechoes.items;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class EchonexSword extends SwordItem {

    private static final int BASE_DURABILITY = 32000;

    public EchonexSword(Properties properties) {
        super(Tiers.NETHERITE, properties.rarity(Rarity.RARE).durability(BASE_DURABILITY));
    }


}
