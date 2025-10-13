package com.github.alecsio.deepechoes.capabilities;

import com.github.alecsio.deepechoes.items.EchoChargedItem;
import com.github.alecsio.deepechoes.registries.DataComponentRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class EchoHandler implements IEchoHandler, INBTSerializable<CompoundTag> {

    private final ItemStack stack;
    private int maxEcho;
    private int storedEcho = 0;

    public EchoHandler(ItemStack stack) {
        Optional.ofNullable(stack.get(DataComponentRegistry.ECHO_CHARGE)).ifPresentOrElse(charge -> {
            maxEcho = charge.maxEcho();
            storedEcho = charge.storedEcho();
        }, () -> maxEcho = (int) Math.pow(2, 16));

        this.stack = stack;
    }

    @Override
    public int insertEcho(int amount, boolean simulate) {
        if (amount <= 0) return 0;

        int space = maxEcho - storedEcho;
        int inserted = Math.min(space, amount);
        int remainder = amount - inserted;

        if (!simulate) {
            storedEcho += inserted;
            updateStack();
        }

        return remainder;
    }

    @Override
    public int extractEcho(int amount, boolean simulate) {
        if (amount <= 0) return 0;

        int extracted = Math.min(storedEcho, amount);

        if (!simulate) {
            storedEcho -= extracted;
            updateStack();
        }

        return extracted;
    }

    @Override
    public int getStoredEcho() {
        return storedEcho;
    }

    @Override
    public int getMaxEcho() {
        return maxEcho;
    }

    private void updateStack() {
        EchoChargedItem.EchoCharge newEchoCharge = new EchoChargedItem.EchoCharge(storedEcho, maxEcho);
        stack.set(DataComponentRegistry.ECHO_CHARGE.get(), newEchoCharge);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        var tag = getCompoundTag();
        tag.putInt("echoAmount", storedEcho);
        tag.putInt("maxEcho", maxEcho);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        storedEcho = nbt.getInt("echoAmount");
        maxEcho = nbt.getInt("maxEcho");
    }

    private CompoundTag getCompoundTag() {
        return new CompoundTag();
    }
}
