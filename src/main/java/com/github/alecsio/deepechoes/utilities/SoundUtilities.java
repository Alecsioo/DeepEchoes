package com.github.alecsio.deepechoes.utilities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.Random;

public class SoundUtilities {

    private static final int OFFSET_UPPER_BOUND = 5;

    public static void playEchoDepleted(ServerLevel level, ServerPlayer player, Random random) {
        level.playSound(null, player.blockPosition().offset(getBoundedRandom(random, OFFSET_UPPER_BOUND), getBoundedRandom(random, OFFSET_UPPER_BOUND), getBoundedRandom(random, OFFSET_UPPER_BOUND)), SoundEvents.WARDEN_AGITATED, SoundSource.AMBIENT);
    }

    private static int getBoundedRandom(Random random, int max) {
        return random.nextInt(max);
    }
}
