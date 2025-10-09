package com.github.alecsio.deepechoes.capabilities;

import com.github.alecsio.deepechoes.DeepEchoes;

public class EchoHandler implements IEchoHandler {

    public static final int MAX_ECHO = (int) Math.pow(2, 16);

    private int echoAmount = 0;

    @Override
    public int insertEcho(int amount, boolean simulate) {
        if (amount <= 0) return 0;

        int space = MAX_ECHO - echoAmount;
        int inserted = Math.min(space, amount);
        int remainder = amount - inserted;

        if (!simulate) {
            echoAmount += inserted;
        }

        return remainder;
    }

    @Override
    public int extractEcho(int amount, boolean simulate) {
        if (amount <= 0) return 0;

        int extracted = Math.min(echoAmount, amount);

        if (!simulate) {
            echoAmount -= extracted;
        }

        return extracted;
    }

    @Override
    public int getStoredEcho() {
        return echoAmount;
    }
}
