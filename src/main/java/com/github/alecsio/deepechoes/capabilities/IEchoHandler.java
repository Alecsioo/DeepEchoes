package com.github.alecsio.deepechoes.capabilities;

public interface IEchoHandler {

    int insertEcho(int amount, boolean simulate);

    int extractEcho(int amount, boolean simulate);

    int getStoredEcho();

    int getMaxEcho();
}
